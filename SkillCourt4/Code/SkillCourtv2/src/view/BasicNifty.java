package view;

import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;

import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglBatchRenderBackendCoreProfileFactory;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;

/**
 *
 * @author Underscore
 */
public class BasicNifty {

    int width;
    int height;
    Nifty niftyMain;
    LwjglInputSystem system;
    DisplayMode dpMode;

    public BasicNifty(int width, int height) throws Exception {
        this.width = width;
        this.height = height;
        
        //LWJGL Dependencies
        initLWJGL();
        initGL();

        //Initialize Input Systems and nifty;
        LwjglInputSystem inputSystem = initInput();
        niftyMain = initNifty(inputSystem);

        
    }

    public Nifty getNifty() {
        return niftyMain;
    }

    
    public void gotoScreen(String in){
        niftyMain.gotoScreen(in);
    }
    
        ///Build Code
    public void renderLoop() {
        boolean done = false;
        while (!Display.isCloseRequested() && !done) {
            Display.update();
            if (niftyMain.update()) {
                done = true;
            }
            niftyMain.render(true);
            int error = GL11.glGetError();
            if (error != GL11.GL_NO_ERROR) {
                String glerrmsg = GLU.gluErrorString(error);
                System.err.println(glerrmsg);
            }
        }
    }

    private void initLWJGL() throws Exception {
        DisplayMode currentMode = Display.getDisplayMode();
        DisplayMode[] modes = Display.getAvailableDisplayModes();
        List<DisplayMode> matching = new ArrayList<DisplayMode>();
        for (int i = 0; i < modes.length; i++) {
            DisplayMode mode = modes[i];
            if (mode.getWidth() == this.width
                    && mode.getHeight() == this.height
                    && mode.getBitsPerPixel() == 32) {
                matching.add(mode);
            }
        }

        DisplayMode[] matchingModes = matching.toArray(new DisplayMode[0]);
        boolean found = false;
        for (int i = 0; i < matchingModes.length; i++) {
            if (matchingModes[i].getFrequency() == currentMode.getFrequency()) {
                Display.setDisplayMode(matchingModes[i]);
                found = true;
                break;
            }
        }

        if (!found) {
            Arrays.sort(matchingModes, new Comparator< DisplayMode>() {
                public int compare(final DisplayMode o1, final DisplayMode o2) {
                    if (o1.getFrequency() > o2.getFrequency()) {
                        return 1;
                    } else if (o1.getFrequency() < o2.getFrequency()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

            for (int i = 0; i < matchingModes.length; i++) {
                Display.setDisplayMode(matchingModes[i]);
                break;
            }
        }

        int x = (currentMode.getWidth() - Display.getDisplayMode().getWidth()) / 2;
        int y = (currentMode.getHeight() - Display.getDisplayMode().getHeight()) / 2;
        Display.setLocation(x, y);
        Display.setFullscreen(false);
        Display.create(new PixelFormat(), new ContextAttribs(3, 2).withProfileCore(true));
        Display.setVSyncEnabled(false);
        Display.setTitle("Hello Nifty");
    }

    private void initGL() {
        glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL11.GL_COLOR_BUFFER_BIT);
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private Nifty initNifty(final LwjglInputSystem inputSystem) throws Exception {
        return new Nifty(
                new BatchRenderDevice(LwjglBatchRenderBackendCoreProfileFactory.create()),
                new NullSoundDevice(),
                inputSystem,
                new AccurateTimeProvider());
    }

    public static class MyScreenController extends DefaultScreenController {

        @NiftyEventSubscriber(id = "exit")
        public void exit(final String id, final ButtonClickedEvent event) {
            nifty.exit();
        }
    }

    private LwjglInputSystem initInput() throws Exception {
        LwjglInputSystem inputSystem = new LwjglInputSystem();
        inputSystem.startup();
        system = inputSystem;
        return inputSystem;
    }

    public void shutDown() {
        system.shutdown();
        Display.destroy();
        System.exit(0);
    }
}

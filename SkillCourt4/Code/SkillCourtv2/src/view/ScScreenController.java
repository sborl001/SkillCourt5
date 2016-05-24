/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.DefaultScreenController;

/**
 *
 * @author Underscore
 */

    public class ScScreenController extends DefaultScreenController {

        @NiftyEventSubscriber(id = "00")
        public void exit(final String id, final ButtonClickedEvent event) {
            nifty.exit();
        }
        
        @NiftyEventSubscriber(id = "01")
        public void action01(final String id, final ButtonClickedEvent event) {
            System.out.println("01 was clicked");
        }
    }


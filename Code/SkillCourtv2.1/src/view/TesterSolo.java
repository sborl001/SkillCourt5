package view;



public class TesterSolo {

    

    public static void main(final String[] args) throws Exception {

        ScScreenController SSC = new ScScreenController();

        BasicNifty runner = new BasicNifty(1024, 768);

        runner.getNifty().loadStyleFile("nifty-default-styles.xml");
        runner.getNifty().loadControlFile("nifty-default-controls.xml");

        runner.getNifty().fromXml("xml_screens/tester.xml", "Screen", SSC);
        
        runner.gotoScreen("SkillCourt");

        runner.renderLoop();
        runner.shutDown();

    }

}

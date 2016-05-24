package skillcourt;


import java.util.HashMap;
import java.util.LinkedList;


public class SkillCourtTester {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
                TileGrid test = new TileGrid("North",3,8);
		TileGrid test2  = new TileGrid("South",2,10);
		
		GridCommand testCommand = new GridCommand("North",0,0,255,0,0,0);
		GridCommand testCommand2 = new GridCommand("North",1,2,255,255,0,1);
		Step testStep = new Step(2L,4L);
		testStep.addCommand(testCommand);
		//GridPrinter.printGrid(test);
		test.takeStep(testStep);
		//GridPrinter.printGrid(test);
		test.zeroOut();
		//GridPrinter.printGrid(test);
		testStep.addCommand(testCommand2);
		test.takeStep(testStep);
		//GridPrinter.printGrid(test);
		
		Step testStep2 = new Step(10L, 20L);
		GridCommand testCommand3 = new GridCommand("North",0,0,255,0,0,0);
		GridCommand testCommand4 = new GridCommand("South",1,2,255,255,0,1);
		testStep2.addCommand(testCommand3);
		testStep2.addCommand(testCommand4);
		
		
		
		
		//Sequence File requires(HashMap<String,String> info, LinkedList<GridInfo> grids, LinkedList<Step> steps)
		HashMap<String, String> info = new HashMap<>();
		info.put("Author", "Puche Luis");
		info.put("Type", "Standard");
		info.put("Name", "FirstSave");
		
		LinkedList<GridInfo> grids = new LinkedList<>();
		grids.add(new GridInfo("North", 3, 4));
		grids.add(new GridInfo("South", 4, 5));
		grids.add(new GridInfo("East", 2,2));
		
		LinkedList<Step> steps = new LinkedList<>();
		steps.add(testStep);
		steps.add(testStep2);
		
		
		HashMap<String, TileGrid> grids2 = new HashMap<>();
		grids2.put(test.getName(), test);
		grids2.put(test2.getName(), test2);
		
		
		
		
		SequenceFile seqFil = new SequenceFile(info,grids,steps);
		
		//seqFil.exportFile("newSave.xml");
		
		Simulator sim = new Simulator(AuxFunction.XMLdecode("newSave.xml"));
		sim.runSimulation();
		

		
		
	}

}

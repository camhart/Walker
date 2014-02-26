


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


public class ImageMapFrame extends JFrame {

	public ImageMapFrame() throws ClassNotFoundException, IOException {
		//1380, 1946 - start
		//finish - 1335, 1615
		this.setTitle("A* Search Algorithm - CamHart");
		this.setName("A* Search Algorithm - CamHart");
		this.setLayout(new BorderLayout());
		//this.setPreferredSize(new Dimension(300, 300));
		//ImageMap iMap = new ImageMap("transformed_map");

		//ImageMapData imd = new ImageMapData(iMap);

		MapPanel mapPanel = new MapPanel("transformed_map");
//
//		//System.out.println("Loading path...");
//		List<Coord> completePath = new ArrayList<Coord>();
//		//new Coord(1958, 652)
//		long startTime = System.currentTimeMillis();
//		//1655, 37 - edgeville
//		//new Coord(1975, 200) ,  varrockish  no possible path found
//		//FindPath fp = new FindPath(iMap, new Coord(1956, 700) , new Coord(1925, 550));
//		FindPath.setImageMap(iMap);
//		//FindPath.setPositions(new Coord(2083, 1691), new Coord(2628, 1451));
//		//FindPath.setPositions(new Coord(2083, 1691), new Coord(2868, 1967));
////		FindPath.setPositions(new Coord(1269, 2244), new Coord(2472, 2428));
//		//FindPath.setPositions(new Coord(2341, 316), new Coord(2363, 1441));
//		//highway path... Searching starting at [1760, 1864 (2.4142135623730
//		 //[1322, 1496 (1.0)]
//		//FindPath.setPositions(new Coord(1760, 1864) , new Coord(1322, 1496));
//		FindPath.setPositions(new Coord(1018, 1665) , new Coord(1010, 1665));
//
//		System.out.print("Searching for highway... ");
//		FindPath startFP = FindPath.getStartInstance();
//		FindPath.setDestinationToFinish();
//		List<Coord> pathToHighway = startFP.findPathToHighway();
//		System.out.println("found!");
//
//
//
//		System.out.print("Searching for destination from highway...");
//		FindPath finishFP = FindPath.getFinishInstance();
//		FindPath.setDestinationToStart();
//		List<Coord> pathFromHighway = finishFP.findPathToHighway();
//		System.out.println("found! " + pathFromHighway);
//
////
//		System.out.print("Searching for highway path... ");
//		FindPath highwayFP = FindPath.getHighwayInstance();
//		FindPath.setDestinationToHighwayFinish();
//		List<Coord> highwayPath = highwayFP.findHighwayPath();
//		System.out.println("found!");
//
//		Collections.reverse(pathFromHighway);
//		completePath.addAll(pathToHighway);
//		completePath.addAll(highwayPath);
//		completePath.addAll(pathFromHighway);

				//new FindPath(new Coord(2626, 1924) , new Coord(2631, 1720));
		//mapPanel.setPath(imd.findPath());
		//mapPanel.setPath(fp.findPath());
		long startTime = System.currentTimeMillis();
		//1010
		List<Coord> completePath = Walker.generatePath(new Coord(1025, 1657) , new Coord(900, 1680));

		mapPanel.setPath(completePath);
		System.out.print("[" + getRunTime(startTime) + "]");

		//Walkx script = new Walkx(null);
		int[] x = new int[0];
		int[] y = new int[0];
		int[][] coords = Walkx.buildCoordsTest(completePath); //script.buildCoords(completePath);
		x = coords[0];
		y = coords[1];
		System.out.println("\nBuilt coords:");
		System.out.println(Arrays.toString(x));
		System.out.println(Arrays.toString(y));

		mapPanel.saveImage();


		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().add(mapPanel);

		this.add(scroll, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ImageMapFrame frame;
				try {
					frame = new ImageMapFrame();
					frame.setDefaultCloseOperation(ImageMapFrame.EXIT_ON_CLOSE);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public String getRunTime(long startTime) {
		long diff = System.currentTimeMillis() - startTime;
		String days = ((diff / (60 * 60 * 1000)) / 24) + "";
		if(days.length() == 1)
			days = "0" + days;
		String hours = ((diff / (60 * 60 * 1000)) % 24) + "";
		if(hours.length() == 1)
			hours = "0" + hours;
		String minutes = ((diff / (1000 * 60)) % 60) + "";
		if(minutes.length() == 1)
			minutes = "0" + minutes;
		String seconds = ((diff / 1000) % 60) + "";
		if(seconds.length() == 1)
			seconds = "0" + seconds;
		return "" + (Integer.parseInt(days) > 0 ? (days + " days, ") : "") + hours + " hours, " + minutes + " minutes, " + seconds + " seconds.";
	}
}

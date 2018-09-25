//package edu.nd.sarec.railwaycrossing.model.infrastructure;
package railwaycrossing.model.infrastructure;
import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;

//import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
import railwaycrossing.model.infrastructure.gate.CrossingGate;

/**
 * Creates all infrastructure for the simulation
 * @author jane
 *
 */
public class MapBuilder {
	HashMap<String, Road> roads;
	HashMap<String, CrossingGate> gates;
	HashMap<String, RailwayTracks> tracks;

	public MapBuilder(){
		roads = new HashMap<String,Road>();
		gates = new HashMap<String,CrossingGate>();
		tracks = new HashMap<String,RailwayTracks>();
		buildRoads();
		buildCrossingGates();
		buildTracks();
		assignGatesToRoads();
		buildCarFactories();
	}

	private void buildRoads(){
		roads.put("Western Highway",new Road(new Point(800,0),new Point (800,1000),Direction.SOUTH,true,false));
		roads.put("Skyway",new Road(new Point(400,0),new Point (400,1000),Direction.SOUTH,true,false));
		roads.put("EastWest",new Road(new Point(415,600),new Point (785,600),Direction.WEST,true,true));
	}

	private void buildCrossingGates(){
		gates.put("Gate1", new CrossingGate(780,280, "Gate1"));
		gates.put("Gate2", new CrossingGate(380,280, "Gate2"));
		//gates.put("GateTop1", new CrossingGate(780,330, "GateTop1"));
		//gates.put("GateTop2", new CrossingGate(380,330, "GateTop2"));
	}

	private void buildTracks(){
		tracks.put("Royal", new RailwayTracks(new Point(0,300),new Point(1200,300)));
		tracks.put("LtoR", new RailwayTracks(new Point(0,400),new Point(1200,400)));
	}

	private void assignGatesToRoads(){
		roads.get("Skyway").assignGate(gates.get("Gate2"), gates.get("Gate1"), null);
		roads.get("Western Highway").assignGate(gates.get("Gate1"), gates.get("Gate2"), null);
		//roads.get("Skyway").assignGate(gates.get("Gate2"), gates.get("Gate1"), null);
		//roads.get("Western Highway").assignGate(gates.get("GateTop1"));
		//roads.get("Skyway").assignGate(gates.get("GateTop2"));
	}

	private void buildCarFactories(){
		roads.get("Western Highway").addCarFactory(gates.get("Gate2"), null);
		roads.get("Skyway").addCarFactory(gates.get("Gate1"), null);
		roads.get("Skyway").addCFObserver(roads.get("Western Highway").getCarFactory());
		//roads.get("EastWest").addCarFactory();
	}

	public Collection<CrossingGate> getAllGates(){
		return gates.values();
	}

	public Collection<RailwayTracks> getTracks(){
		return tracks.values();
	}

	public Collection<Road> getRoads(){
		return roads.values();
	}

	public RailwayTracks getTrack(String name){
		return tracks.get(name);
	}
}
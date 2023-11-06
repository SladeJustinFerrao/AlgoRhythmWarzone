package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import Models.*;
import Services.MapService;
import Services.PlayerServices;
import Utils.Command;
import Views.MapView;

/**
 * This is the entry point of the Game and keeps the track of current Game State.
 */
public class GameEngine {

	/**
	 * d_gameState stores the information about current GamePlay.
	 */
	public GameState d_gameState = new GameState();

	/**
	 * d_mapService instance is used to handle load, read, parse, edit, and save map file.
	 */
	public MapService d_mapService = new MapService();

	/**
	 * Player Service instance to edit players and issue orders.
	 */
	PlayerServices d_playerService = new PlayerServices();

	/**
	 *  Throwable Exception e
	 */
	private Throwable e;

	/**
	 * getD_gameState is a getter method to get current game state.
	 *
	 * @return the current game state
	 */
	public GameState getD_gameState() {
		return d_gameState;
	}

	/**
	 * The main method responsible for accepting command from users and redirecting
	 * those to corresponding logical flows.
	 *
	 * @param p_args the program doesn't use default command line arguments
	 */
	public static void main(String[] p_args) {
		GameEngine l_game = new GameEngine();

		l_game.initGamePlay();
	}

	/**
	 * Handle the commands.
	 *
	 * @param p_enteredCommand command entered by the user in CLI
	 * @throws Exception indicates Exception e
	 */
	public void handleCommand(String p_enteredCommand) throws Exception {
		Command l_command = new Command(p_enteredCommand);
		String l_rootCommand = l_command.getMainCommand();
		boolean l_isMapLoaded = d_gameState.getD_map() != null;

		switch (l_rootCommand) {
			case "editmap": {
				performEditMap(l_command);
				break;
			}
			case "editcontinent": {
				if (!l_isMapLoaded) {
					System.out.println("Can not edit continent, please perform `editmap` first");
					break;
				}
				performEditContinent(l_command);
				break;
			}
			case "savemap": {
				if (!l_isMapLoaded) {
					System.out.println("No map found to save, Please `editmap` first");
					break;
				}
				performSaveMap(l_command);
				break;
			}
			case "loadmap": {
				performLoadMap(l_command);
				break;
			}
			case "validatemap": {
				if (!l_isMapLoaded) {
					System.out.println("No map is found for validation, Please loadmap & editmap first");
					break;
				}
				performValidateMap(l_command);
				break;
			}
			case "editcountry": {
				if (!l_isMapLoaded) {
					System.out.println("Can not edit country, please perform editmap first");
					break;
				}
				performEditCountry(l_command);
				break;
			}
			case "editneighbour": {
				if (!l_isMapLoaded) {
					System.out.println("Can not edit neighbors, please perform editmap first");
					break;
				}
				performEditNeighbour(l_command);
				break;
			}
			case "gameplayer": {
				if (!l_isMapLoaded) {
					System.out.println("No map found, Please loadmap before adding game players");
					break;
				}
				createPlayers(l_command);
				break;
			}
			case "assigncountries": {
				assignCountries(l_command);
				break;
			}
			case "showmap": {
				MapView l_mapView = new MapView(d_gameState);
				l_mapView.showMap();
				break;
			}
			case "exit": {
				System.out.println("Exit Command Entered");
				System.exit(0);
				break;
			}
			default: {
				System.out.println("Invalid Command");
				break;
			}
		}
	}

	/**
	 * This method initiates the CLI to accept commands from user and maps them to corresponding action handler.
	 */
	private void initGamePlay() {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				System.out.println("Enter game commands or type 'exit' for quitting");
				String l_commandEntered = l_reader.readLine();
				handleCommand(l_commandEntered);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Basic validation of <strong>editmap</strong> command for
	 * checking arguements and directing control to model for the processing.
	 *
	 * @param p_command command by the user on the CLI
	 * @throws Exception indicates Exception e
	 */
	public void performEditMap(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

		if (l_operations_list == null || l_operations_list.isEmpty()) {
			System.out.println(e.getMessage());
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (!l_map.isEmpty() && l_map.containsKey("arguments") && l_map.get("arguments") != null) {
					try {
						d_mapService.editMap(d_gameState, l_map.get("arguments"));
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				} else {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	/**
	 * Sets and displays GameEngine logs.
	 *
	 * @param p_gameEngineLog The log message to be added.
	 * @param p_logType The type of log (e.g., phase, error, info).
	 */
	public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
		String l_consoleLogger = p_logType.toLowerCase().equals("phase")
				? "\n************ " + p_gameEngineLog + " ************\n"
				: p_gameEngineLog;
		System.out.println(l_consoleLogger);
	}

	/**
	 * Basic validation of <strong>editcontinent</strong> command for
	 * checking arguements and directing control to model for the processing.
	 *
	 * @param p_command command by the user on the CLI
	 * @throws Exception indicates Exception e
	 */
	public void performEditContinent(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

		if (l_operations_list == null || l_operations_list.isEmpty()) {
			System.out.println(e.getMessage());
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (!l_map.isEmpty() && l_map.containsKey("arguments") && l_map.get("arguments") != null) {
					try {
						d_mapService.editContinent(d_gameState, l_map.get("arguments"), l_map.get("operation"));
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				} else {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	/**
	 *
	 * Basic validation of <strong>savemap</strong> command for
	 * checking arguements and directing control to model for the processing.
	 *
	 * @param p_command command by the user on the CLI
	 * @throws Exception indicates Exception e
	 */
	public void performSaveMap(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(e.getMessage());
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (!l_map.isEmpty() && l_map.containsKey("arguments") && l_map.get("arguments") != null) {
					boolean l_fileUpdateStatus = false;
					try {
						l_fileUpdateStatus = d_mapService.saveMap(d_gameState, l_map.get("arguments"));
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
					if (l_fileUpdateStatus)
						System.out.println("Required changes have been made in the map file");
					else
						System.out.println(d_gameState.getError());
				} else {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	/**
	 *
	 * Basic validation of <strong>loadmap</strong> command for
	 * checking arguments and directing control to model for the processing.
	 *
	 * @param p_command command by the user on the CLI
	 * @throws Exception indicates Exception e
	 */
	public void performLoadMap(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(e.getMessage());
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (!l_map.isEmpty() && l_map.containsKey("arguments") && l_map.get("arguments") != null) {
					Models.Map l_mapToLoad = d_mapService.loadMap(d_gameState, d_mapService.getFilePath(l_map.get("arguments")));
					if (l_mapToLoad.Validate()) {
						System.out.println("Map is loaded successfully. \n");
					} else {
						d_mapService.resetState(d_gameState);
					}
				} else {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	/**
	 *
	 * Basic validation of <strong>validatemap</strong> command for checking required arguments and
	 * redirecting control to model for actual processing.
	 *
	 * @param p_command command by the user on the CLI
	 * @throws Exception indicates Exception e
	 */
	public void performValidateMap(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			Models.Map l_currentMap = d_gameState.getD_map();
			if (l_currentMap == null) {
				System.out.println(e.getMessage());
			} else {
				if (l_currentMap.Validate()) {
					System.out.println("The loaded map is valid!");
				} else {
					System.out.println("Failed to Validate map!");
				}
			}
		} else {
			System.out.println(e.getMessage());
		}
	}

	/**
	 *
	 * Basic validation of <strong>editcountry</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command by the user on the CLI
	 * @throws Exception indicates Exception e
	 */
	public void performEditCountry(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(e.getMessage());
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (!l_map.isEmpty() && l_map.containsKey("arguments") && l_map.get("arguments") != null) {
					d_mapService.editCountry(d_gameState, l_map.get("arguments"), l_map.get("operation"));
				} else {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>editneighbor</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws Exception indicates Exception e
	 */
	public void performEditNeighbour(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(e.getMessage());
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (!l_map.isEmpty() && l_map.containsKey("arguments") && l_map.get("arguments") != null) {
					d_mapService.editNeighbour(d_gameState, l_map.get("arguments"), l_map.get("operation"));
				} else {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>gameplayer</strong> command for checking required
	 * arguments and redirecting control to model for adding or removing players.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws Exception indicates Exception e
	 */
	public void createPlayers(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(e.getMessage());
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (!l_map.isEmpty() && l_map.containsKey("arguments") && l_map.get("arguments") != null) {
					d_playerService.updatePlayers(d_gameState, l_map.get("operation"), l_map.get("arguments"));
				} else {
					System.out.println(e.getMessage());
				}
			}
		}
	}


	/**
	 * Basic validation of <strong>assigncountries</strong> for checking required
	 * arguments and redirecting control to model for assigning countries to players.
	 *
	 * @param p_command command entered by the user on CLI
  	 * @throws Exception indicates Exception e
	 */
	public void assignCountries(Command p_command) throws Exception {
		List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
		if (l_operations_list.size()==0) {
			d_playerService.assignCountries(d_gameState);

			while (d_gameState.getD_players().size() != 0) {
				System.out.println("\n********Starting Main Game***********\n");

				d_playerService.assignArmies(d_gameState);

				while (d_playerService.unassignedArmiesExists(d_gameState.getD_players())) {
					for (Player l_player : d_gameState.getD_players()) {
						if (l_player.getD_noOfUnallocatedArmies() != null && l_player.getD_noOfUnallocatedArmies() != 0)
							l_player.issue_order();
					}
				}

				while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
					for (Player l_player : d_gameState.getD_players()) {
						Order l_order = l_player.next_order();
						if (l_order != null)
							l_order.execute(d_gameState, l_player);
					}
				}
				MapView l_map_view = new MapView(d_gameState, d_gameState.getD_players());
				l_map_view.showMap();

				System.out.println("Press Y to move ahead for next turn or else press N");
				BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
				String l_continue = l_reader.readLine();
				if (l_continue.equalsIgnoreCase("N"))
					break;
			}
		} else {
			System.out.println(e.getMessage());
		}
	}
}

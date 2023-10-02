package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import Constants.GameConstants;
import Models.GameState;
import Models.Order;
import Models.Player;
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
	GameState d_gameState = new GameState();

	/**
	 * d_mapService instance is used to handle load, read, parse, edit, and save map file.
	 */
	MapService d_mapService = new MapService();

	/**
	 * Player Service instance to edit players and issue orders.
	 */
	PlayerServices d_playerService = new PlayerServices();

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
	 * @throws InvalidMap indicates map is invalid
	 * @throws InvalidCommand indicates command is invalid
	 * @throws IOException indicates failure in I/O operation
	 */
	public void handleCommand(String p_enteredCommand) throws Exception {
		Command l_command = new Command(p_enteredCommand);
		String l_rootCommand = l_command.getMainCommand();
		boolean l_isMapLoaded = d_gameState.getD_map() != null;

		switch (l_rootCommand) {
			case "editmap": {
				performMapEdit(l_command);
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
				break;
			}
			case "editcountry": {
				if (!l_isMapLoaded) {
					System.out.println("Can not edit country, please perform editmap first");
					break;
				}
				performValidateMap(l_command);
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
				System.out.println("Enter Game Commands or type 'exit' for quitting");
				String l_commandEntered = l_reader.readLine();

				// command for hanlding
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} 
		}
	}

	/**
	 * Basic validation of <strong>editmap</strong> command for checking required
	 * arguments and redirecting control to model for actual processing.
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws IOException indicates when failure in I/O operation
	 * @throws InvalidCommand indicates when command is invalid
	 */
	public void performMapEdit(Command p_command) throws Exception {
		//List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

	}

	/**
	 * Basic validation of <strong>editcontinent</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws IOException indicates failure in I/O operation
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap indicates map is invalid
	 */
	public void performEditContinent(Command p_command) throws Exception {
		//implement
	}

	/**
	 * Basic validation of <strong>savemap</strong> command for checking required
	 * arguments and redirecting control to model for actual processing.
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidMap indicates when map is invalid
	 * @throws InvalidCommand indicates when command is invalid
	 */
	public void performSaveMap(Command p_command) throws Exception {
		//save map
	}

	/**
	 * Basic validation of <strong>loadmap</strong> command for checking required arguments and
	 * redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates when command is invalid
	 */
	private void performLoadMap(Command p_command) throws Exception {
		

		
			//load map using map class modal
	}

	/**
	 * Basic validation of <strong>validatemap</strong> command for checking required arguments and
	 * redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates when command is invalid
	 * @throws InvalidMap indicates when map is invalid
	 */
	private void performValidateMap(Command p_command) throws Exception {
		//validate the map
	}

	/**
	 * Basic validation of <strong>editcountry</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap indicates map is invalid
	 */
	public void performEditCountry(Command p_command) throws Exception {
		
			//edit country
	}

	/**
	 * Basic validation of <strong>editneighbor</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap indicates map is invalid
	 */
	public void performEditNeighbour(Command p_command) throws Exception {
		
		
			//edit neighbour
	}

	/**
	 * Basic validation of <strong>gameplayer</strong> command for checking required
	 * arguments and redirecting control to model for adding or removing players.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 */
	private void createPlayers(Command p_command) throws Exception {
		
			//create player
	}

	/**
	 * Basic validation of <strong>assigncountries</strong> for checking required
	 * arguments and redirecting control to model for assigning countries to players.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	public void assignCountries(Command p_command) throws Exception {
		//assign counrties
	}
}

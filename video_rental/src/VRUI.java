import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;

	private static CustomerManager customerManager = new CustomerManager();

	private static VideoManager videoManager = new VideoManager();

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;

		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: customerManager.listCustomers(); break ;
				case 2: videoManager.listVideos() ; break ;
				case 3: ui.registerCustomer(); ; break ;
				case 4: ui.registerVideo(); break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport(); break;
				case 8: ui.clearRentals() ; break ;
				case -1: ui.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye");
	}

	private Customer getCustomer() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		return customerManager.getCustomer(customerName);
	}

	public void clearRentals() {
		Customer foundCustomer = getCustomer();

		if ( foundCustomer == null ) {
			System.out.println("No customer found") ;
		} else {
			System.out.println("Name: " + foundCustomer.getName() +
					"\tRentals: " + foundCustomer.getRentals().size()) ;
			for ( Rental rental: foundCustomer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}

			List<Rental> rentals = new ArrayList<Rental>() ;
			foundCustomer.setRentals(rentals);
		}
	}

	public void returnVideo() {
		Customer foundCustomer = getCustomer();
		if ( foundCustomer == null ) return ;

		System.out.println("Enter video title to return: ") ;
		String videoTitle = scanner.next() ;
		videoManager.returnVideo(foundCustomer, videoTitle);
	}

	public void getCustomerReport() {
		Customer foundCustomer = getCustomer();
		String result = customerManager.makeReport(foundCustomer);
		int totalPoint = customerManager.getTotalPoint(foundCustomer.getRentals());
		if (totalPoint >= 10) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if (totalPoint >= 30) {
			System.out.println("Congrat! You earned two free coupon");
		}
		System.out.println(result);
	}

	private void init() {
		Customer james = new Customer("James") ;
		Customer brown = new Customer("Brown") ;
		customerManager.addCustomer(james) ;
		customerManager.addCustomer(brown); ;

		Video v1 = new Video("v1", VideoType.CD, Video.REGULAR, new Date()) ;
		Video v2 = new Video("v2", VideoType.DVD, Video.NEW_RELEASE, new Date()) ;
		videoManager.addVideo(v1); ;
		videoManager.addVideo(v2) ;

		Rental r1 = new Rental(v1) ;
		Rental r2 = new Rental(v2) ;

		james.addRental(r1) ;
		james.addRental(r2) ;
	}

	public void rentVideo() {
		Customer foundCustomer = getCustomer();

		if ( foundCustomer == null ) return ;

		System.out.println("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;
		videoManager.rentVideo(foundCustomer, videoTitle);
	}

	public void registerCustomer() {
		System.out.println("Enter customer name: ");
		String name = scanner.next();
		customerManager.registerCustomer(name);
	}

	private void registerVideo() {
		System.out.println("Enter video title to register: ") ;
		String title = scanner.next() ;

		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
		int videoType = scanner.nextInt();

		System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		int priceCode = scanner.nextInt();

		Date registeredDate = new Date();
		Video video = new Video(title, VideoType.fromValue(videoType), priceCode, registeredDate) ;
		videoManager.addVideo(video);
	}

	public int showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt() ;

		return command ;

	}
}

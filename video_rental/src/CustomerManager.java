import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    private List<Customer> customers = new ArrayList<Customer>();

    public List<Customer> getCustomerList() {
        return customers;
    }

    public Customer findCustomer(String customerName) {
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }

    public Customer getCustomer(String customerName) {
        return findCustomer(customerName);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void listCustomers() {
        System.out.println("List of customers");
        for ( Customer customer: getCustomerList() ) {
            System.out.println("Name: " + customer.getName() +
                    "\tRentals: " + customer.getRentals().size()) ;
            for ( Rental rental: customer.getRentals() ) {
                System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
                System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
            }
        }
        System.out.println("End of list");
    }

    public void getCustomerReport(String customerName) {
        Customer foundCustomer = findCustomer(customerName);

        if ( foundCustomer == null ) {
            System.out.println("No customer found") ;
        } else {
            String result = makeReport(foundCustomer) ;
            System.out.println(result);
        }
    }

    void registerCustomer(String name) {
        Customer customer = new Customer(name) ;
        addCustomer(customer);
    }

    public double getTotalCharge(List<Rental> rentals) {
        double totalCharge = 0;

        for(Rental rental : rentals) {
            totalCharge += rental.getCharge();
        }

        return totalCharge;
    }

    public int getTotalPoint(List<Rental> rentals) {
        int totalPoint = 0;

        for (Rental rental : rentals) {
            totalPoint += rental.getPoint();;
        }

        return totalPoint;
    }

    public String makeReport(Customer customer) {
        String result = "Customer Report for " + customer.getName() + "\n";

        List<Rental> rentals = customer.getRentals();

        double totalCharge = getTotalCharge(rentals);
        int totalPoint = getTotalPoint(rentals);

        for (Rental each : rentals) {
            double eachCharge = each.getCharge();
            int eachPoint = each.getPoint();
            int daysRented = each.getDaysRented();

            result += "\t" + each.getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
                    + "\tPoint: " + eachPoint + "\n";
        }

        result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

        return result ;
    }
}



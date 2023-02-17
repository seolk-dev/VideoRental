import java.util.Date;

public class Rental {
    private Video video;
    private RentalStatusType status;
    private Date rentDate;
    private Date returnDate;

    enum RentalStatusType {
        RENTED,
        RETURNED,
    }

    public Rental(Video video) {
        this.video = video;
        status = RentalStatusType.RENTED;
        rentDate = new Date();
    }

    public Video getVideo() {
        return video;
    }

    public RentalStatusType getStatus() {
        return status;
    }

    public void returnVideo() {
        if (status == RentalStatusType.RENTED) {
            this.status = RentalStatusType.RETURNED;
            returnDate = new Date();
        }
    }

    public Date getRentDate() {
        return rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public int getDaysRentedLimit() {
        if (getDaysRented() <= 2) return 0;
        return video.getLimit();
    }

    public int getDaysRented() {
        long diff;
        if (getStatus() == RentalStatusType.RETURNED) {
            diff = getReturnDate().getTime() - getRentDate().getTime();
        } else {
            diff = new Date().getTime() - getRentDate().getTime();
        }
        return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
    }

    public double getCharge() {
        double charge = 0;
        int daysRented = getDaysRented();

        if (isNewRelease()) {
            return daysRented * 3;
        }

        charge += 2;
        if (daysRented > 2) {
            charge += (daysRented - 2) * 1.5;
        }

        return charge;
    }

    public int getPoint() {
        int point = 0;
        int daysRented = getDaysRented();

        if (isNewRelease()) {
            point++;
        }

        if (daysRented > getDaysRentedLimit()) {
            point -= Math.min(point, getLateReturnPointPenalty());
        }
        return point;
    }

    public boolean isNewRelease() {
        return getVideo().getPriceCode() == Video.NEW_RELEASE;
    }

    public int getLateReturnPointPenalty() {
        return getVideo().getLateReturnPointPenalty();
    }

    public String getTitle() {
        return getVideo().getTitle();
    }
}

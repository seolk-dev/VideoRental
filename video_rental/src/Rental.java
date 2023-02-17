import java.util.Date;

public class Rental {
	private Video video ;
	private RentalStatusType status ;
	private Date rentDate ;
	private Date returnDate ;

	enum RentalStatusType {
		RENTED,
		RETURNED,
	}

	public Rental(Video video) {
		this.video = video ;
		status = RentalStatusType.RENTED ;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public RentalStatusType getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == RentalStatusType.RETURNED ) {
			this.status = RentalStatusType.RETURNED;
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented = getDaysRented();
		if ( daysRented <= 2) return limit ;

		limit = video.getLimit(limit);
		return limit ;
	}

	public int getDaysRented() {
		int daysRented;
		if (getStatus() == RentalStatusType.RETURNED) { // returned Video
			long diff = getReturnDate().getTime() - getRentDate().getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - getRentDate().getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		return daysRented;
	}

	public double getCharge() {
		double charge = 0;
		int daysRented = getDaysRented();

		if (isNewRelease()) {
			charge = daysRented * 3;

		} else {
			charge += 2;
			if (daysRented > 2)
				charge += (daysRented - 2) * 1.5;
		}

		return charge;
	}

	public int getPoint() {
		int point = 0;
		int daysRented = getDaysRented();

		if(isNewRelease()) {
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

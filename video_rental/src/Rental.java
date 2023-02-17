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

	public double getEachCharge(double eachCharge, int daysRented) {
		switch (getVideo().getPriceCode()) {
			case Video.REGULAR:
				eachCharge += 2;
				if (daysRented > 2)
					eachCharge += (daysRented - 2) * 1.5;
				break;
			case Video.NEW_RELEASE:
				eachCharge = daysRented * 3;
				break;
		}
		return eachCharge;
	}

}

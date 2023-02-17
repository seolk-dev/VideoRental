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

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented ;
		if (getStatus() == RentalStatusType.RETURNED) { // returned Video
			long diff = returnDate.getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		if ( daysRented <= 2) return limit ;

		//video로 옮겨가도 되지않나.
		limit = video.getLimit(limit);
		return limit ;
	}

}

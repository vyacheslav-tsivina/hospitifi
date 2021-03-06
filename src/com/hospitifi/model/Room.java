package com.hospitifi.model;

import java.util.List;
import java.util.Objects;

public class Room {
    private long id;
    private String number;
    private int floor;
    private int beds;
    private BedType bedType;
    private boolean safe;
    private boolean bath;
    private int rateCategory;
    private int rate;

    private List<Occupation> occupations;
    private List<Reservation> reservations;

    public Room() {
    }

    public Room(long id, String number, int floor, int beds, BedType bedType, boolean safe, boolean bath, int rateCategory, int rate) {
        this.id = id;
        this.number = number;
        this.floor = floor;
        this.beds = beds;
        this.bedType = bedType;
        this.safe = safe;
        this.bath = bath;
        this.rateCategory = rateCategory;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public boolean hasSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean hasBath() {
        return bath;
    }

    public void setBath(boolean bath) {
        this.bath = bath;
    }

    public int getRateCategory() {
        return rateCategory;
    }

    public void setRateCategory(int rateCategory) {
        this.rateCategory = rateCategory;
    }

    public List<Occupation> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<Occupation> occupations) {
        this.occupations = occupations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                floor == room.floor &&
                beds == room.beds &&
                safe == room.safe &&
                bath == room.bath &&
                rateCategory == room.rateCategory &&
                rate == room.rate &&
                Objects.equals(number, room.number) &&
                bedType == room.bedType &&
                Objects.equals(occupations, room.occupations) &&
                Objects.equals(reservations, room.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, floor, beds, bedType, safe, bath, rateCategory, rate, occupations, reservations);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Room{");
        sb.append("id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append(", floor=").append(floor);
        sb.append(", beds=").append(beds);
        sb.append(", bedType=").append(bedType);
        sb.append(", safe=").append(safe);
        sb.append(", bath=").append(bath);
        sb.append(", rateCategory=").append(rateCategory);
        sb.append(", rate=").append(rate);
        sb.append(", occupations=").append(occupations);
        sb.append(", reservations=").append(reservations);
        sb.append('}');
        return sb.toString();
    }

}

package com.example.beender.model;

import com.google.firebase.firestore.auth.User;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CurrentItems {
    // Static variable reference of single_instance
    // of type Singleton
    private static CurrentItems single_instance = null;

    private HashMap<Integer, ArrayList<ItemModel>> currStack;
    private HashMap<Integer, ArrayList<ItemModel>> swipedRight;
    private HashMap<String, List<LatLng>> archiveMap;
    private UserTrip currArchive;

    private ItemModel chosenHotel;
    private ArrayList<ItemModel> currStackHotels;
    private int currDay;

    private String nextPageToken;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private CurrentItems()
    {
        currStack = new HashMap<>();
        swipedRight = new HashMap<>();
        archiveMap = new HashMap<>();
        currStackHotels = new ArrayList<>();
        currDay = 0;

        currStack.put(0, new ArrayList<>());
        swipedRight.put(0, new ArrayList<>());
    }

    // Static method
    // Static method to create instance of Singleton class
    public static CurrentItems getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentItems();

        return single_instance;
    }


    public HashMap<Integer, ArrayList<ItemModel>> getSwipedRight() {
        return swipedRight;
    }

    public void setSwipedRight(HashMap<Integer, ArrayList<ItemModel>> swipedRight) {
        this.swipedRight = swipedRight;
    }

    public HashMap<Integer, ArrayList<ItemModel>> getCurrStack() {
        return currStack;
    }

    public ArrayList<ItemModel> getCurrStackHotels() {
        return currStackHotels;
    }

    public void setCurrStackHotels(ArrayList<ItemModel> currStackHotels) {
        this.currStackHotels = currStackHotels;
    }

    public ItemModel getChosenHotel() {
        return chosenHotel;
    }

    public void setChosenHotel(ItemModel chosenHotel) {
        this.chosenHotel = chosenHotel;
    }

    public int getCurrDay() {
        return currDay;
    }

    public HashMap<String, List<LatLng>> getArchiveMap() {
        return archiveMap;
    }

    public void setArchiveMap(HashMap<String, List<LatLng>> archiveMap) {
        this.archiveMap = archiveMap;
    }

    public UserTrip getCurrArchive() {
        return currArchive;
    }

    public void setCurrArchive(UserTrip currArchive) {
        this.currArchive = currArchive;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    // METHODS
    public List<LatLng> getAsLatLng (int day) {
        if(swipedRight != null) {
            List<LatLng> list = new ArrayList<>();
            for(ItemModel item : swipedRight.get(day)) {
                list.add(new LatLng(item.getLat(), item.getLng()));
            }
            return list;
        }
        return null;
    }

    public void addToSwipedRight(ItemModel item) {
        if(swipedRight == null) return;
        swipedRight.get(currDay).add(item);
    }

    public void nextDay() {
        if(swipedRight == null) return;
        currDay += 1;
        swipedRight.put(currDay, new ArrayList<>());

        // Set the chosen hotel as the starting point of this day's route
        swipedRight.get(currDay).add(chosenHotel);
    }

    public void reset() {
        single_instance = null;
    }
}

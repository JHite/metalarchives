package com.github.loki.afro.metallum.entity;

import com.github.loki.afro.metallum.enums.DiscType;

import java.awt.image.BufferedImage;
import java.util.*;

public class Disc extends AbstractEntity {

    private DiscType type;
    private List<Track> trackList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();
    private String releaseDate;
    private Band band = new Band();
    private Label label = new Label();
    private BufferedImage artwork = null;
    private String details = "";
    private String artworkURL = null;
    /**
     * Only filled if DiscType.isSplit(type) is true
     * Does only contain BandName and Id.
     */
    private List<Band> splitBands = new ArrayList<>();

    private Map<Member, String> lineup = new HashMap<>();
    private Map<Member, String> miscMember = new HashMap<>();
    private Map<Member, String> guestMember = new HashMap<>();

    private int discCount = 1;
    private boolean hasReviews = false;

    public Disc(final long id) {
        super(id);
    }

    public Disc() {
        super(0);
    }

    public final float getReviewPercentAverage() {
        float k = 0;
        for (final Review review : this.reviewList) {
            k += review.getPercent();
        }
        return k / this.reviewList.size();
        // calc
    }

    public final boolean hasReviews() {
        return this.hasReviews;
    }

    public int getReviewCount() {
        return this.reviewList.size();
    }

    public DiscType getType() {
        return this.type;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(final String date) {
        this.releaseDate = date;
    }

    public int getTrackCount() {
        return this.trackList.size();
    }

    public List<Review> getReviews() {
        return this.reviewList;
    }

    public void setBandName(final String bandName) {
        this.band.setName(bandName);
    }

    public void setDiscType(final DiscType discType) {
        this.type = discType;
    }

    public void setGenre(final String genre) {
        this.band.setGenre(genre);
    }

    public Band getBand() {
        return this.band;
    }

    public Label getLabel() {
        return this.label;
    }

    public void setLabel(final Label label) {
        this.label = label;
    }

    public String getGenre() {
        return this.band.getGenre();
    }

    public final void addReview(final Review... reviews) {
        for (final Review review : reviews) {
            review.setDisc(this);
            this.reviewList.add(review);
            this.hasReviews = true;
        }
    }

    public void setBand(final Band band) {
        this.band = band;
        this.band.addToDiscography(this);
    }

    public static List<Disc> setBandForEachDisc(final List<Disc> discList, final Band band) {
        for (final Disc disc : discList) {
            disc.setBand(band);
        }
        return discList;
    }

    public void addTracks(final Track... tracks) {
        Collections.addAll(this.trackList, tracks);
    }

    public void setArtwork(final BufferedImage artwork) {
        this.artwork = artwork;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public boolean isSplit() {
        return DiscType.isSplit(this.type);
    }

    public BufferedImage getArtwork() {
        return this.artwork;
    }

    public String getDetails() {
        return this.details;
    }

    public void addSplitBand(final Band... bands) {
        for (final Band band : bands) {
            band.addToDiscography(this);
            this.splitBands.add(band);
        }
    }

    public void setLineup(final Map<Member, String> lineUp) {
        this.lineup = lineUp;
    }

    public void setMiscLineup(final Map<Member, String> miscMember) {
        this.miscMember = miscMember;
    }

    public void setGuestLineup(final Map<Member, String> guestMember) {
        this.guestMember = guestMember;
    }

    /**
     * @return the trackList
     */
    public List<Track> getTrackList() {
        return this.trackList;
    }

    public List<Track> getTrackListOnDisc(final int discNumber) {
        final List<Track> trackListByDiscNumber = new ArrayList<>();
        for (final Track track : this.trackList) {
            if (track.getDiscNumber() == discNumber) {
                trackListByDiscNumber.add(track);
            }

        }
        return trackListByDiscNumber;
    }

    /**
     * @return the lineup
     */
    public Map<Member, String> getLineup() {
        return this.lineup;
    }

    /**
     * @return the miscMember
     */
    public Map<Member, String> getMiscMember() {
        return this.miscMember;
    }

    /**
     * @return the guestMember
     */
    public Map<Member, String> getGuestMember() {
        return this.guestMember;
    }

    public List<Band> getSplitBands() {
        return this.splitBands;
    }

    public int getDiscCount() {
        return this.discCount;
    }

    protected void setDiscCount(final int discCount) {
        this.discCount = discCount;
    }

    public String getBandName() {
        return this.band.getName();
    }

    public final boolean hasArtwork() {
        return this.artwork != null;
    }

    public void setReviews(final List<Review> reviewList) {
        this.reviewList = reviewList;
        this.hasReviews = !reviewList.isEmpty();
    }

    public void setSplitBands(final List<Band> splitBands2) {
        this.splitBands = splitBands2;
    }

    public final void setTrackList(final List<Track> newTrackList) {
        this.trackList = newTrackList;
    }

    public String getArtworkURL() {
        return this.artworkURL;
    }

    public void setArtworkURL(final String artworkURL) {
        this.artworkURL = artworkURL;
    }

    public final void setHasReview(final boolean hasReviews) {
        this.hasReviews = hasReviews;
    }

    /**
     * This Method is for convenience only.
     * It calls {@link Disc#getLineup()}, {@link Disc#getMiscMember()} and {@link Disc#getGuestMember()} and puts them all together in one map.
     *
     * @return a map with all Member, where the value the role at the specific album;
     */
    public Map<Member, String> getMember() {
        Map<Member, String> completeMemberMap = getLineup();
        completeMemberMap.putAll(getMiscMember());
        completeMemberMap.putAll(getGuestMember());
        return completeMemberMap;
    }

}
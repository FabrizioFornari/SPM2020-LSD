package lsd.smartparking.model;

import com.google.firebase.database.annotations.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.geo.Point;
import org.springframework.util.Assert;

public class Coords {

    @NotNull
    @Range(min = -180, max = 180)
    private double x;
    @NotNull
    @Range(min = -90, max = 90)
    private double y;


    @PersistenceConstructor
    public Coords(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public Coords(double[] coords) {
        this(coords[0], coords[1]);
		Assert.isTrue(coords.length == 2, "Coords array has to have 2 elements");
    }

    public double getX() {
        return x;
    }

    public final void setX(double x) {
		Assert.notNull(x, "X (longitude) cannot be null");
		Assert.isTrue((x >= -180 && x <= 180), "X (longitude) must be in the range between -180 and 180");
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public final void setY(double y) {
		Assert.notNull(y, "Y (latitude) cannot be null");
		Assert.isTrue((y >= -90 && y <= 90), "Y (latitude) must be in the range between -90 and 90");
        this.y = y;
    }
    
    @Transient
    public double getLon() {
        return getX();
    }

    @Transient
    public double getLat() {
        return getY();
    }

    public Point toPoint() {
        return new Point(this.x, this.y);
    }
    
}

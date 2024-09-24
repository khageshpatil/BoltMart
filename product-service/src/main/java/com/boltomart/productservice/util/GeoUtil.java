package com.boltomart.productservice.util;

public class GeoUtil {

    private static final double EARTH_RADIUS = 6371.0; 
    
    public static Double[] calculateBoundingBox(Double latitude, Double longitude, Double radius) {
      
        double latRadians = Math.toRadians(latitude);
        double lonRadians = Math.toRadians(longitude);
        
        double radiusInKm = radius / 1000.0;

        double latOffset = Math.toDegrees(radiusInKm / EARTH_RADIUS);
        double minLat = latitude - latOffset;
        double maxLat = latitude + latOffset;

        double lonOffset = Math.toDegrees(radiusInKm / (EARTH_RADIUS * Math.cos(latRadians)));
        double minLon = longitude - lonOffset;
        double maxLon = longitude + lonOffset;

        minLat = Math.max(minLat, -90.0);
        maxLat = Math.min(maxLat, 90.0);
        minLon = Math.max(minLon, -180.0);
        maxLon = Math.min(maxLon, 180.0);

        return new Double[] {minLat, maxLat, minLon, maxLon};
    }
}

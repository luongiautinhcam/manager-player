package vn.edu.stu.quanlycauthu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

public class MapActivity extends AppCompatActivity {
    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        addControls();
    }

    private void addControls() {
        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                setupMap();
            }
        });
    }

    private void setupMap() {
        //STU: 10.738013326292622, 106.67782617085916
        Point pointSTU = Point.fromLngLat(106.67782617085916,10.738013326292622);

        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(
                mapView
        );
        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(
                annotationPlugin,
                new AnnotationConfig()
        );
        PointAnnotationOptions annotationOptions = new PointAnnotationOptions()
                .withTextField("STU nè")
                .withPoint(pointSTU)
                .withIconImage(BitmapFactory.decodeResource(
                        this.getResources(),
                        R.drawable.red_marker
                ));
        pointAnnotationManager.create(annotationOptions);

        CameraOptions cameraOptions = new CameraOptions.Builder()
                .center(pointSTU)
                .zoom(15.5)
                .build();
        mapView.getMapboxMap().setCamera(cameraOptions);
    }
}
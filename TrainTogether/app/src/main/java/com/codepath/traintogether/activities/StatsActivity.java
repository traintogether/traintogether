package com.codepath.traintogether.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.traintogether.R;
import com.codepath.traintogether.utils.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements OnDataPointListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private static final String TAG = "StatsActivity";
    private boolean authInProgress = false;
    private GoogleApiClient mApiClient;
    OnDataPointListener mSpeedListener;
    OnDataPointListener mLocationListener;
    OnDataPointListener mDistanceListener;

    @BindView(R.id.tvStats)
    TextView tvStats;
    @BindView(R.id.tvSpeed)
    TextView tvSpeed;
    @BindView(R.id.tvDistance)
    TextView tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove(mApiClient, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mApiClient.disconnect();
                        }
                    }
                });
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual
            findDataSources();

        }
    }

    private void findDataSources() {
/*        DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes( DataType.TYPE_STEP_COUNT_ )
                .setDataSourceTypes( DataSource.TYPE_DERIVED )
                .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                for( DataSource dataSource : dataSourcesResult.getDataSources() ) {
                    if( DataType.TYPE_SPEED.equals( dataSource.getDataType() ) ) {
                        registerFitnessDataListener(dataSource, DataType.TYPE_SPEED);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);*/

        Fitness.SensorsApi.findDataSources(mApiClient, new DataSourcesRequest.Builder()
                // At least one datatype must be specified.
                .setDataTypes(DataType.TYPE_SPEED)
                // Can specify whether data type is raw or derived.
                .setDataSourceTypes(DataSource.TYPE_DERIVED)
                .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            Log.i(TAG, "Data source found: " + dataSource.toString());
                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                            //Let's register a listener to receive Activity data!
                            if (dataSource.getDataType().equals(DataType.TYPE_SPEED)
                                    && mSpeedListener == null) {
                                Log.i(TAG, "Data source for TYPE_SPEED found!  Registering.");
                                registerFitnessDataListener(dataSource,
                                        DataType.TYPE_SPEED, Constants.REQUEST_TYPE_SPEED);
                            }
                        }
                    }
                });

        // Note: Fitness.SensorsApi.findDataSources() requires the ACCESS_FINE_LOCATION permission.
        Fitness.SensorsApi.findDataSources(mApiClient, new DataSourcesRequest.Builder()
                // At least one datatype must be specified.
                .setDataTypes(DataType.TYPE_LOCATION_SAMPLE)
                // Can specify whether data type is raw or derived.
                .setDataSourceTypes(DataSource.TYPE_RAW)
                .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            Log.i(TAG, "Data source found: " + dataSource.toString());
                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                            //Let's register a listener to receive Activity data!
                            if (dataSource.getDataType().equals(DataType.TYPE_LOCATION_SAMPLE)
                                    && mLocationListener == null) {
                                Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
                                registerFitnessDataListener(dataSource,
                                        DataType.TYPE_LOCATION_SAMPLE, Constants.REQUEST_TYPE_LOCATION);
                            }
                        }
                    }
                });


        // Note: Fitness.SensorsApi.findDataSources() requires the ACCESS_FINE_LOCATION permission.
        Fitness.SensorsApi.findDataSources(mApiClient, new DataSourcesRequest.Builder()
                // At least one datatype must be specified.
                .setDataTypes(DataType.AGGREGATE_DISTANCE_DELTA)
                // Can specify whether data type is raw or derived.
                .setDataSourceTypes(DataSource.TYPE_DERIVED)
                .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            Log.i(TAG, "Data source found: " + dataSource.toString());
                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                            //Let's register a listener to receive Activity data!
                            if (dataSource.getDataType().equals(DataType.AGGREGATE_DISTANCE_DELTA)
                                    && mLocationListener == null) {
                                Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
                                registerFitnessDataListener(dataSource,
                                        DataType.AGGREGATE_DISTANCE_DELTA, Constants.REQUEST_AGGREGATE_DISTANCE_DELTA);
                            }
                        }
                    }
                });
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == Constants.REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                findDataSources();

            } else {
                // Permission was denied or request was cancelled
                Log.i("DEBUG", "Request was denied");
            }
        }
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType, int REQUEST_STAT_TYPE) {

        switch (REQUEST_STAT_TYPE){
            case Constants.REQUEST_TYPE_SPEED:
                mSpeedListener = new OnDataPointListener() {
                    @Override
                    public void onDataPoint(DataPoint dataPoint) {
                        for (Field field : dataPoint.getDataType().getFields()) {
                            Float val = dataPoint.getValue(field).asFloat();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float metersPerSecondToMilesPerHour = (float) 2.23694;
                                    Calendar c = Calendar.getInstance();
                                    tvSpeed.setText("Current speed: " + String.valueOf(val * metersPerSecondToMilesPerHour));
                                    Log.i(TAG, "Detected DataPoint field: " + field.getName());
                                    Log.i(TAG, "Detected DataPoint value: " + String.valueOf(val * metersPerSecondToMilesPerHour));
                                }
                            });
                        }
                    }
                };

                Fitness.SensorsApi.add(
                        mApiClient,
                        new SensorRequest.Builder()
                                .setDataSource(dataSource) // Optional but recommended for custom data sets.
                                .setDataType(dataType) // Can't be omitted.
                                .build(),
                        mSpeedListener)
                        .setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    Log.i(TAG, "Listener registered!");
                                } else {
                                    Log.i(TAG, "Listener not registered.");
                                }
                            }
                        });
                break;
            case Constants.REQUEST_TYPE_LOCATION:
                mLocationListener = new OnDataPointListener() {
                @Override
                public void onDataPoint(DataPoint dataPoint) {
                    for (Field field : dataPoint.getDataType().getFields()) {
                        Value val = dataPoint.getValue(field);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();
                                tvStats.setText(tvStats.getText() + "\n" + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + " Field: " + field.getName() + " Value: " + val);
                                Log.i(TAG, "Detected DataPoint field: " + field.getName());
                                Log.i(TAG, "Detected DataPoint value: " + val);
                            }
                        });


                    }
                }
            };

                Fitness.SensorsApi.add(
                        mApiClient,
                        new SensorRequest.Builder()
                                .setDataSource(dataSource) // Optional but recommended for custom data sets.
                                .setDataType(dataType) // Can't be omitted.
                                .build(),
                        mLocationListener)
                        .setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    Log.i(TAG, "Listener registered!");
                                } else {
                                    Log.i(TAG, "Listener not registered.");
                                }
                            }
                        });
                break;
            case Constants.REQUEST_AGGREGATE_DISTANCE_DELTA:
                mDistanceListener = new OnDataPointListener() {
                    @Override
                    public void onDataPoint(DataPoint dataPoint) {
                        for (Field field : dataPoint.getDataType().getFields()) {
                            Value val = dataPoint.getValue(field);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Calendar c = Calendar.getInstance();
                                    tvDistance.setText("Distance: " + val);
                                    Log.i(TAG, "Detected DataPoint field: " + field.getName());
                                    Log.i(TAG, "Detected DataPoint value: " + val);
                                }
                            });
                        }
                    }
                };

                break;

        }





    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!authInProgress) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult(StatsActivity.this, REQUEST_OAUTH);
            } catch (IntentSender.SendIntentException e) {

            }
        } else {
            Log.e("GoogleFit", "authInProgress");
        }
    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {
        for (final Field field : dataPoint.getDataType().getFields()) {
            final Value value = dataPoint.getValue(field);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "Field: " + field.getName() + " Value: " + value);
                    Toast.makeText(getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                    Calendar c = Calendar.getInstance();
                    tvStats.setText(tvStats.getText() + "\n" + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + " Field: " + field.getName() + " Value: " + value);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                if (!mApiClient.isConnecting() && !mApiClient.isConnected()) {
                    mApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("GoogleFit", "RESULT_CANCELED");
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    public void startActivityTracking(View view) {


    }

    public void stopActivityTracking(View view) {
    }
}
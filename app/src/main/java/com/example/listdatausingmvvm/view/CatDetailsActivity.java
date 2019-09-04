package com.example.listdatausingmvvm.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.listdatausingmvvm.R;
import com.example.listdatausingmvvm.databinding.ActivityCatDetailsBinding;
import com.example.listdatausingmvvm.model.CatDto;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;


public class CatDetailsActivity extends AppCompatActivity implements ExoPlayer.EventListener {

    private DataSource.Factory dataSourceFactory;
    private MediaSource mediaSource;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCatDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cat_details);

        initializeAudioPlayer();
        final String catEntryKey = "cat_entry";
        if (getIntent() != null && getIntent().hasExtra(catEntryKey)) {

            CatDto catEntry = getIntent().getParcelableExtra(catEntryKey);
            Glide.with(getApplicationContext()).load(catEntry.getImageUrl()).dontAnimate().fitCenter()
                    .placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder).into(binding.catImageView);
            binding.descriptionTextView.setText(catEntry.getDescription());
            binding.titleTextView.setText(catEntry.getTitle());
        }

        binding.playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(RawResourceDataSource.buildRawResourceUri(R.raw.cat));
                player.prepare(mediaSource);
            }
        });
    }


    void initializeAudioPlayer() {
        RenderersFactory renderersFactory = new DefaultRenderersFactory(this);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector, loadControl);
        player.addListener(this);
        dataSourceFactory = new DefaultDataSourceFactory(this, "catsDemo");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}

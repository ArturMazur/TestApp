package com.example.testapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PAGE_COUNT = "page_count";
    private ViewPager viewPager;
    private PagerAdapterFragment adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Init UI
        initUI();
        // Load number of fragment from SharedPreferences
        loadPage();
        // Load fragment after notification
        initAdapterAfterNotification();
    }

    private void initUI() {
        // Buttons
        ImageButton createNewNotification = findViewById(R.id.createNewNotification);
        ImageButton deleteFragment = findViewById(R.id.btn_delete_fragment);
        ImageButton addFragment = findViewById(R.id.btn_add_fragment);

        createNewNotification.setOnClickListener(this);
        addFragment.setOnClickListener(this);
        deleteFragment.setOnClickListener(this);

        // Init ViewPager and PagerAdapterFragment
        viewPager = findViewById(R.id.viewPager);
        adapter = new PagerAdapterFragment(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void initAdapterAfterNotification() {
        int pageCountFromNotification = getIntent().getIntExtra(PAGE_COUNT, 0);
        if (pageCountFromNotification > 0) {
            createFragmentsAndOpenLast(pageCountFromNotification);
        }
    }

    private void createFragmentsAndOpenLast(int pageCount) {
        for (int i = 0; i < pageCount; i++) {
            adapter.addFragment(PageFragment.newInstance(i + 1), String.valueOf(i + 1));
        }
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_fragment:
                addFragment();
                break;
            case R.id.btn_delete_fragment:
                deleteFragment();
                destroyNotification();
                break;
            case R.id.createNewNotification:
                createNotification();
                break;
        }
    }

    private void deleteFragment() {
        if (adapter.getCount() > 0) {
            adapter.removeFragment();
        }
    }

    private void addFragment() {
        adapter.addFragment(PageFragment.newInstance(adapter.getCount() + 1), String.valueOf(adapter.getCount() + 1));
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    private void createNotification() {
        if (adapter.getCount() > 0) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(PAGE_COUNT, adapter.getCount());
            int idNotification = viewPager.getCurrentItem();
            NotificationHelper.createNotification(this, intent, idNotification + 1);
        }
    }

    private void destroyNotification() {
        NotificationHelper.destroyNotification(viewPager.getCurrentItem());
    }

    @Override
    protected void onPause() {
        super.onPause();

        savePage();
    }

    // Save fragment to SharedPreferences
    private void savePage() {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt(PAGE_COUNT, adapter.getCount())
                .apply();
    }

    // Load fragment from SharedPreferences
    private void loadPage() {
        int pageCount = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt(PAGE_COUNT, 0);
        if (pageCount > 0) {
            createFragmentsAndOpenLast(pageCount);
        }
    }
}

package net.lucode.hackware.magicindicatordemo.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.lucode.hackware.magicindicatordemo.R;

public class ExampleMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_main_layout);
    }

    public void onClick(View view) {
        if (view.getId()==R.id.scrollable_tab) {
            startActivity(new Intent(this, ScrollableTabExampleActivity.class));
        }else if(view.getId()==R.id.fixed_tab){
            startActivity(new Intent(this, FixedTabExampleActivity.class));
        }else if(view.getId()==R.id.dynamic_tab){
            startActivity(new Intent(this, DynamicTabExampleActivity.class));
        }else if(view.getId()==R.id.no_tab_only_indicator){
            startActivity(new Intent(this, NoTabOnlyIndicatorExampleActivity.class));
        }else if(view.getId()==R.id.tab_with_badge_view){
            startActivity(new Intent(this, BadgeTabExampleActivity.class));
        }else if(view.getId()==R.id.work_with_fragment_container){
            startActivity(new Intent(this, FragmentContainerExampleActivity.class));
        }else if(view.getId()==R.id.load_custom_layout){
            startActivity(new Intent(this, LoadCustomLayoutExampleActivity.class));
        }else if(view.getId()==R.id.custom_navigator){
            startActivity(new Intent(this, CustomNavigatorExampleActivity.class));
        }
    }
}

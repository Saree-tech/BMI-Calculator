package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Spinner spinnerWeightUnit, spinnerHeightUnit;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        spinnerWeightUnit = findViewById(R.id.spinnerWeightUnit);
        spinnerHeightUnit = findViewById(R.id.spinnerHeightUnit);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        // Weight options
        String[] weightUnits = {"Kilograms (kg)", "Pounds (lb)"};
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightUnits);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeightUnit.setAdapter(weightAdapter);

        // Height options
        String[] heightUnits = {"Meters (m)", "Centimeters (cm)", "Feet (ft)"};
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, heightUnits);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeightUnit.setAdapter(heightAdapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String weightStr = etWeight.getText().toString();
                String heightStr = etHeight.getText().toString();

                if (weightStr.isEmpty() || heightStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both values", Toast.LENGTH_SHORT).show();
                    return;
                }

                float weight = Float.parseFloat(weightStr);
                float height = Float.parseFloat(heightStr);

                // Convert weight to kilograms if needed
                String weightUnit = spinnerWeightUnit.getSelectedItem().toString();
                if (weightUnit.contains("Pound")) {
                    weight = weight * 0.453592f; // pounds to kg
                }

                // Convert height to meters if needed
                String heightUnit = spinnerHeightUnit.getSelectedItem().toString();
                if (heightUnit.contains("Centimeter")) {
                    height = height / 100f; // cm to meters
                } else if (heightUnit.contains("Feet")) {
                    height = height * 0.3048f; // feet to meters
                }

                float bmi = weight / (height * height);

                String category;
                if (bmi < 18.5)
                    category = "Underweight 😕";
                else if (bmi < 25)
                    category = "Normal weight ✅";
                else if (bmi < 30)
                    category = "Overweight ⚠️";
                else
                    category = "Obese ❌";

                tvResult.setText(String.format("BMI: %.2f\n%s", bmi, category));
            }
        });
    }
}

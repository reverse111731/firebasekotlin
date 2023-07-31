package com.example.firebasekotlin.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.R
import com.example.firebasekotlin.model.EmployeeModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity: AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {

            //getting values
            val empName = etEmpName.text.toString()
            val empAge = etEmpAge.text.toString()
            val empSalary = etEmpSalary.text.toString()
            if (empName.isNotEmpty() && empAge.isNotEmpty() && empAge.isNotEmpty()){
                saveEmployeeData(empName, empAge, empSalary)
            }
            if (empName.isEmpty()) {
                etEmpName.error = "Please enter name"
            }
            if (empAge.isEmpty()) {
                etEmpAge.error = "Please enter age"
            }
            if (empSalary.isEmpty()) {
                etEmpSalary.error = "Please enter salary"
            }

        }
    }

    private fun saveEmployeeData(
        empName: String,
        empAge: String,
        empSalary: String
    ) {

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, empName, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpSalary.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
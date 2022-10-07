package com.onedev.kodev

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object Data {
    @Parcelize
    data class Doctor(
        val image: String,
        val name: String,
        val specialist: String,
        val experience: String,
        val price: String,
    ): Parcelable

    fun generateDataDoctor(): ArrayList<Doctor> {
        val categories = ArrayList<Doctor>()
        categories.clear()

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/doctor-bulk-billing-doctors-chapel-hill-health-care-medical-3.png",
                "Dr. Jimmy Andrean",
                "Dokter Umum",
                "5 Tahun",
                "Rp25.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/png-woman-doctor-transparent-woman-doctor-images-40.png",
                "Dr. Steffany Tan",
                "Dokter Kulit",
                "4 Tahun",
                "Rp25.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/basic-ideas-for-logical-programs-for-doctor-home-loan-6.png",
                "Dr. Robertson",
                "Dokter Jantung",
                "7 Tahun",
                "Rp30.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/doctor-yashfeen-hospital-navsari-39.png",
                "Dr. Layla Sar",
                "Dokter Umum",
                "5 Tahun",
                "Rp32.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/png-woman-doctor-transparent-woman-doctor-images-37.png",
                "Dr. Maya Jan",
                "Dokter Anak",
                "10 Tahun",
                "Rp50.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/png-woman-doctor-transparent-woman-doctor-images-17.png",
                "Dr. Jessica Ker",
                "Dokter Umum",
                "5 Tahun",
                "Rp25.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/dentist-doctor-bhubaneswar-medical-doctor-36.png",
                "Dr. Anthony",
                "Dokter Umum",
                "6 Tahun",
                "Rp30.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/doctor-bikowski-skin-care-center-sewickey-pennsylvania-home-35.png",
                "Dr. Alberto",
                "Dokter Ginjal",
                "20 Tahun",
                "Rp100.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/png-woman-doctor-transparent-woman-doctor-images-11.png",
                "Dr. Mariana",
                "Dokter Umum",
                "9 Tahun",
                "Rp45.000",
            )
        )

        categories.add(
            Doctor(
                "https://www.freepnglogos.com/uploads/doctor-png/doctor-png-transparent-doctor-images-0.png",
                "Dr. Vania",
                "Perawat",
                "3 Tahun",
                "Rp15.000",
            )
        )

        return categories
    }
}
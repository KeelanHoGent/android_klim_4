package com.klimaatmobiel.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.projecten3android.R
import com.klimaatmobiel.ui.viewModels.WebshopViewModel

class ConfirmDeletionDialogFragment: DialogFragment() {

    private lateinit var viewModel: WebshopViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewModel = activity?.run {
            ViewModelProviders.of(this)[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Zeker alles verwijderen?")
                .setPositiveButton("Ja",
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.clearShoppingCart()
                    })
                .setNegativeButton("Nee",
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.onDeletedOrCancelled()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
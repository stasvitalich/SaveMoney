package com.example.savemoney.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.savemoney.R
import com.example.savemoney.databinding.FragmentMenuBinding
import androidx.navigation.Navigation

class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        calculations()
        clearFields()
    }


    private fun calculations(){

        binding.compareButton.setOnClickListener {
            val price1Text = binding.textPrice1.text.toString()
            val weight1Text = binding.textWeight1.text.toString()

            val price2Text = binding.textPrice2.text.toString()
            val weight2Text = binding.textWeight2.text.toString()

            if (price1Text.isEmpty() || weight1Text.isEmpty()) {
                binding.textRecomendation.text = null
            }

            if (price2Text.isEmpty() || weight2Text.isEmpty()) {
                binding.textRecomendation.text = null
            }

            var result1: Double = 0.0
            var result2: Double = 0.0
            // Check if the price and weight values are not empty
            if (price1Text.isNotEmpty() && weight1Text.isNotEmpty()) {
                // Convert the values to Double and calculate the result
                val price1 = price1Text.toDouble()
                val weight1 = weight1Text.toDouble()
                val item1Price = price1 / weight1 * 1000
                result1 = item1Price

                // Round the result to two decimal places
                val formattedResult1 = String.format("%.2f", item1Price)

                // Update the TextView with the rounded result
                //binding.textViewTotal1.text = formattedResult1
            }



            if (price2Text.isNotEmpty() && weight2Text.isNotEmpty()) {
                val price2 = price2Text.toDouble()
                val weight2 = weight2Text.toDouble()
                val item2Price = price2 / weight2 * 1000
                result2 = item2Price

                val formattedResult2 = String.format("%.2f", item2Price)

                // Update the TextView with the unrounded result
            }

            if (price1Text.isNotEmpty() && weight1Text.isNotEmpty() && price2Text.isNotEmpty() && weight2Text.isNotEmpty()) {
                var difference = 0.0

                if (result1 > result2) {
                    difference = ((result1 / result2) - 1) * 100
                } else {
                    difference = ((result2 / result1) - 1) * 100
                }


                val formattedDifference = String.format("%.2f", difference)

                if (result1 > result2) {
                    binding.textRecomendation.text = "Продукт 1 дороже на $formattedDifference%"
                    binding.imageThumb2.visibility = View.VISIBLE
                } else if (result1 < result2) {
                    binding.textRecomendation.text = "Продукт 2 дороже на $formattedDifference%"
                    binding.imageThumb1.visibility = View.VISIBLE
                } else {
                    binding.textRecomendation.text = "Стоимость продуктов одинакова"
                    binding.imageThumb1.visibility = View.INVISIBLE
                    binding.imageThumb2.visibility = View.INVISIBLE

                }
            }
        }
    }

    private fun clearFields(){
        binding.apply {
            clearButton.setOnClickListener {
                textWeight1.text = null
                textPrice1.text = null

                textPrice2.text = null
                textWeight2.text = null

                textRecomendation.text = null

                textInputLayoutWeight1.requestFocus()
                binding.imageThumb1.visibility = View.INVISIBLE
                binding.imageThumb2.visibility = View.INVISIBLE
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MenuFragment()
    }
}
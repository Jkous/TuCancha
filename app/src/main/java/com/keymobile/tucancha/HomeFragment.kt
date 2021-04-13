package com.keymobile.tucancha

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keymobile.tucancha.adapters.PolideportivoAdapter
import com.keymobile.tucancha.entidades.Polideportivo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

    // Adapters
    private var polideportivosAdapter: PolideportivoAdapter? = null
    private var polideportivos: ArrayList<Polideportivo> = ArrayList()

    private val poliAdapter by lazy {
        PolideportivoAdapter(context, polideportivos)
    }

    // Views
    private var rvPolideportivos: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CargarPolideportivos()

/*
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
*/

    }

    fun CargarPolideportivos() {
        polideportivos.add(Polideportivo("Chorrillos", 2, 8.7))
        polideportivos.add(Polideportivo("Barranco", 3, 9.0))
        polideportivos.add(Polideportivo("Callao", 5, 9.8))
        //polideportivosAdapter = PolideportivoAdapter(context, polideportivos)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        rvPolideportivos = view.findViewById<RecyclerView>(R.id.rvPolideportivos)



        return view;
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
package com.example.gasteando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CalendarioFG extends Fragment {

    public CalendarioFG() {
        // Required empty public constructor
    }

    public static CalendarioFG newInstance() {
        CalendarioFG fragment = new CalendarioFG();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendario_f_g, container, false);

        // Obtener una referencia al CalendarView desde la vista inflada
        CalendarView calendarView = rootView.findViewById(R.id.calendarView);

        // Configurar un listener para manejar la selección de fechas
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                    // Obtén la fecha seleccionada en el formato deseado (dayOfMonth, month, year)
                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;

                    if (getActivity() instanceof MenuPrincipal) {
                        MenuPrincipal menuPrincipal = (MenuPrincipal) getActivity();
                        menuPrincipal.setFechaSeleccionada(fechaSeleccionada);

                    // Busca el fragmento GastorealizadoFG en el contenedor
                    GastorealizadoFG gastorealizadoFG = (GastorealizadoFG) getChildFragmentManager().findFragmentByTag("GastorealizadoFG");

                    if (gastorealizadoFG == null) {
                        // Si GastorealizadoFG no existe en el contenedor, créalo
                        gastorealizadoFG = new GastorealizadoFG();
                    }

                    // Actualiza la fecha seleccionada en GastorealizadoFG
                    gastorealizadoFG.setFechaSeleccionada(fechaSeleccionada);

                    // Reemplaza el fragmento actual con GastorealizadoFG o agrégalo si no existe
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, gastorealizadoFG)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return rootView;
    }
}

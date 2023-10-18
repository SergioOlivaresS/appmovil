package com.example.gasteando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarioFG#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_calendario_f_g, container, false);

        // Obtener una referencia al CalendarView desde la vista inflada
        CalendarView calendarView = view.findViewById(R.id.calendarView);

        // Configurar un listener para manejar la selección de fechas
        calendarView.setOnDateChangeListener(this::onSelectedDayChange);

        return view;
    }

    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        // Convertir la fecha seleccionada a una cadena legible
        String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;

        // Mostrar la fecha seleccionada en un Toast (puedes personalizar esto)
        Toast.makeText(getContext(), "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();

        // Llamar al método gastorealizadofg en el fragmento GastorealizadoFG y pasar la fecha seleccionada
        if (getActivity() instanceof MenuPrincipal) {
            MenuPrincipal menuPrincipal = (MenuPrincipal) getActivity();
            menuPrincipal.gastorealizadofg(view);
        }
    }


    private FragmentManager getSupportFragmentManager() {
        return null;
    }
}

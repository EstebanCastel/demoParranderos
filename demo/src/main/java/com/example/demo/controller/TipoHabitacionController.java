package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.modelo.TipoHabitacion;
import com.example.demo.repositorio.TipoHabitacionRepository;
import com.example.demo.service.SequenceGeneratorService;

@Controller
public class TipoHabitacionController {

    @Autowired
    private TipoHabitacionRepository tipoHabitacionRepository;

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @GetMapping("/tiposHabitacion")
    public String obtenerTodosLosTiposHabitacion(Model model) {
        model.addAttribute("tiposHabitacion", tipoHabitacionRepository.findAll());
        return "tiposHabitacion";
    }

    @GetMapping({"/crearTipoHabitacion", "/editarTipoHabitacion"})
    public String mostrarFormularioTipoHabitacion(@RequestParam(required = false) String id, Model model) {
        if (id != null) {
            TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Tipo de Habitación inválido: " + id));
            model.addAttribute("tipoHabitacion", tipoHabitacion);
        } else {
            model.addAttribute("tipoHabitacion", new TipoHabitacion());
        }
        return "tiposHabitacionForm";
    }

    @PostMapping("/guardarTipoHabitacion")
    public String guardarTipoHabitacion(@ModelAttribute("tipoHabitacion") TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion.getId() == null || tipoHabitacion.getId().isEmpty()) {
            tipoHabitacion.setId(String.valueOf(sequenceGenerator.generateSequence(TipoHabitacion.SEQUENCE_NAME)));
        }
        tipoHabitacionRepository.save(tipoHabitacion);
        return "redirect:/tiposHabitacion";
    }

    @PostMapping("/eliminarTipoHabitacion")
    public String eliminarTipoHabitacion(@RequestParam(name = "id") String id) {
        tipoHabitacionRepository.deleteById(id);
        return "redirect:/tiposHabitacion";
    }
}

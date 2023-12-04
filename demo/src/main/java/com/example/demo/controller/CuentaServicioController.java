package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.modelo.CuentaServicio;
import com.example.demo.repositorio.CuentaServicioRepository;

@Controller
public class CuentaServicioController {

    @Autowired
    private CuentaServicioRepository cuentaServicioRepository;

    @GetMapping("/cuentasServicio")
    public String getCuentasServicio(Model model) {
        model.addAttribute("cuentasServicio", cuentaServicioRepository.findAll());
        return "cuentasServicio";
    }

    @GetMapping("/cuentasServicioForm")
    public String mostrarFormulario(Model model) {
        model.addAttribute("nuevaCuentaServicio", new CuentaServicio());
        return "cuentasServicioForm";
    }

    @PostMapping("/crearCuentaServicio")
    public String crearCuentaServicio(@ModelAttribute("nuevaCuentaServicio") CuentaServicio nuevaCuentaServicio) {
        cuentaServicioRepository.save(nuevaCuentaServicio);
        return "redirect:/cuentasServicio";
    }
}

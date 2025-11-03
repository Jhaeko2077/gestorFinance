package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts/list"; 
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("account", new Account());
        return "accounts/form"; 
    }

    @PostMapping
    public String createAccount(@ModelAttribute Account account, RedirectAttributes redirect) {
        accountService.saveAccount(account);
        redirect.addFlashAttribute("success", "Cuenta creada correctamente.");
        return "redirect:/accounts";
    }

    @GetMapping("/edit/{id}")
    public String editAccount(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        model.addAttribute("account", account);
        return "accounts/form";
    }

    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable Long id, @ModelAttribute Account updated, RedirectAttributes redirect) {
        Account existing = accountService.getAccountById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        existing.setName(updated.getName());
        existing.setBalance(updated.getBalance());
        accountService.saveAccount(existing);
        redirect.addFlashAttribute("success", "Cuenta actualizada correctamente.");
        return "redirect:/accounts";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id, RedirectAttributes redirect) {
        accountService.deleteAccount(id);
        redirect.addFlashAttribute("success", "Cuenta eliminada correctamente.");
        return "redirect:/accounts";
    }
}
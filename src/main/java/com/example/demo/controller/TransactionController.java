package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.service.AccountService;
import com.example.demo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        return "transactions/list"; 
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "transactions/form"; 
    }

    @PostMapping
    public String createTransaction(@ModelAttribute Transaction transaction, RedirectAttributes redirect) {
        transactionService.createTransaction(transaction);
        redirect.addFlashAttribute("success", "Transacción registrada correctamente.");
        return "redirect:/transactions";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id, RedirectAttributes redirect) {
        transactionService.deleteTransaction(id);
        redirect.addFlashAttribute("success", "Transacción eliminada correctamente.");
        return "redirect:/transactions";
    }
}
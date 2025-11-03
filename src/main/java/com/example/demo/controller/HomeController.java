package com.example.demo.controller;

import com.example.demo.service.AccountService;
import com.example.demo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public HomeController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalBalance", accountService.getTotalBalance());
        model.addAttribute("accounts", accountService.getAllAccounts());
        model.addAttribute("recentTransactions", transactionService.getRecentTransactions());
        return "index"; 
    }
}
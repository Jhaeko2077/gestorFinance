package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getRecentTransactions() {
        return transactionRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        Account account = transaction.getAccount();

        if (account == null || account.getId() == null) {
            throw new IllegalArgumentException("Debe especificar una cuenta válida.");
        }

        Account managedAccount = accountRepository.findById(account.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));

        BigDecimal amount = transaction.getAmount() == null ? BigDecimal.ZERO : transaction.getAmount();

        if (transaction.getType() == Transaction.Type.INCOME) {
            managedAccount.setBalance(managedAccount.getBalance().add(amount));
        } else if (transaction.getType() == Transaction.Type.EXPENSE) {
            managedAccount.setBalance(managedAccount.getBalance().subtract(amount));
        }

        transaction.setAccount(managedAccount);
        Transaction savedTransaction = transactionRepository.save(transaction);
        accountRepository.save(managedAccount);

        return savedTransaction;
    }

    @Transactional
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada."));

        Account account = transaction.getAccount();

        if (transaction.getType() == Transaction.Type.INCOME) {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        } else if (transaction.getType() == Transaction.Type.EXPENSE) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        }

        transactionRepository.delete(transaction);
        accountRepository.save(account);
    }
}
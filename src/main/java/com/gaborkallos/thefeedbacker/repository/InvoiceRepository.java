package com.gaborkallos.thefeedbacker.repository;

import com.gaborkallos.thefeedbacker.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}

package com.example.springreactivemongodbaggregation.model;

public record InvoiceCountSummary(double total, double paid, double canceled, double pending) {}
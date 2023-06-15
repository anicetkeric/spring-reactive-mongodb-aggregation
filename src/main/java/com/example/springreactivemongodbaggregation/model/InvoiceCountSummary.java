package com.example.springreactivemongodbaggregation.model;

public record InvoiceCountSummary(int total, int paid, int canceled, int pending) {
}
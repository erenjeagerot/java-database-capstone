# Smart Clinic Management System - Database Design

This document outlines the hybrid database architecture for the Smart Clinic Management System, combining relational data storage (MySQL) for structured entities and document-based storage (MongoDB) for flexible or semi-structured data.

---

## 1. Relational Database Design (MySQL)

The relational database is structured to handle core operational entities such as administrators, doctors, patients, and appointments with strict ACID compliance and defined foreign key constraints.

### Tables Overview:
1. **admin**: Stores administrative credentials and profile information.
2. **doctors**: Stores doctor profiles, specialties, and professional details.
3. **patients**: Stores patient demographic and contact information.
4. **appointments**: Manages scheduling links between doctors and patients.

---

### Table Schemas:

#### Table: `admin`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for the administrator |
| `username` | VARCHAR(50) | NOT NULL, UNIQUE | Admin login username |
| `password` | VARCHAR(255) | NOT NULL | Encrypted password |
| `email` | VARCHAR(100) | NOT NULL, UNIQUE | Admin contact email |

#### Table: `doctors`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for the doctor |
| `name` | VARCHAR(100) | NOT NULL | Full name of the doctor |
| `email` | VARCHAR(100) | NOT NULL, UNIQUE | Professional email address |
| `phone` | VARCHAR(20) | NOT NULL | Contact number |
| `specialty` | VARCHAR(100) | NOT NULL | Medical specialty (e.g., Cardiology) |
| `available_times` | VARCHAR(255) | NOT NULL | Available shift/slot details |

#### Table: `patients`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for the patient |
| `name` | VARCHAR(100) | NOT NULL | Full name of the patient |
| `email` | VARCHAR(100) | NOT NULL, UNIQUE | Patient email address |
| `phone` | VARCHAR(20) | NOT NULL, UNIQUE | Patient contact phone number |
| `address` | TEXT | NULL | Residential address |

#### Table: `appointments`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for the appointment |
| `doctor_id` | INT | FOREIGN KEY (`doctors(id)`), NOT NULL | References the attending doctor |
| `patient_id` | INT | FOREIGN KEY (`patients(id)`), NOT NULL | References the visiting patient |
| `appointment_time` | DATETIME | NOT NULL | Scheduled date and time |
| `status` | VARCHAR(50) | DEFAULT 'Scheduled' | Current status (e.g., Scheduled, Completed, Cancelled) |

---

## 2. Document Database Design (MongoDB)

For flexible and evolving data payloads—such as medical prescriptions, diagnostics notes, and dosage guidelines—MongoDB is utilized as a document store.

### Collection: `prescriptions`
Prescriptions often require nested structures to hold multiple medication items, varying dosage frequencies, and specific instructions without forcing rigid table normalizations.

#### Realistic JSON Example:
```json
{
  "_id": {
    "$oid": "64a2f1c8e4b0a12345678901"
  },
  "appointment_id": 1024,
  "doctor_id": 5,
  "patient_id": 12,
  "issued_date": "2026-06-06T10:30:00Z",
  "diagnosis": "Acute Bronchitis",
  "medications": [
    {
      "drug_name": "Amoxicillin",
      "dosage": "500mg",
      "frequency": "3 times a day",
      "duration_days": 7,
      "instructions": "Take after meals with a full glass of water."
    },
    {
      "drug_name": "Paracetamol",
      "dosage": "650mg",
      "frequency": "As needed for fever",
      "duration_days": 3,
      "instructions": "Do not exceed 4 doses in 24 hours."
    }
  ],
  "doctor_notes": "Patient advised to rest for 3 days and stay hydrated. Follow up if symptoms persist."
}
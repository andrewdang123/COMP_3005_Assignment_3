#!/bin/bash
# Compile the project

DB_NAME="school"
DB_USER="postgres"

# Check if database exists
if psql -U "$DB_USER" -lqt | cut -d \| -f 1 | grep -qw "$DB_NAME"; then
  echo "Database '$DB_NAME' already exists."
else
  echo "Creating database '$DB_NAME'..."
  createdb -U "$DB_USER" "$DB_NAME"
  echo "Database '$DB_NAME' created successfully."
fi

mvn compile

# Run the main application
mvn exec:java -Dexec.mainClass="PopulateDatabase"
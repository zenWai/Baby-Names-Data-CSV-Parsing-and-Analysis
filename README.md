# Baby Names Data CSV Parsing and Analysis
This Java project is designed to analyze the data of US baby names from CSV files. The project offers a range of functionalities to provide insights into various aspects of baby names data.

# Features
### 1. Total Births
The program calculates the total number of births, as well as the total number of boys, girls, boy names, and girl names in a selected CSV file or files.

### 2. Rank of a Baby Name
This functionality retrieves the popularity rank of a baby name in a specified year and gender from the CSV file. The most popular name is given a rank of 1.

### 3. Name Associated with a Rank
This feature returns the name associated with a provided rank, year, and gender based on the number of births. If the rank is not found, it returns "NO NAME FOUND".

### 4. Year with the Highest Rank for a Given Name
The program can return the year in which a given name and gender achieved the highest popularity rank. If the name is not found, it returns -1.

### 5. Average Rank of a Name
The program can calculate the average popularity rank of a provided name and gender over all available years. If the name is not ranked, it returns -1.

### 6. Total Number of Births Ranked Higher
This functionality returns the total number of births for names ranked more popular than a given name.

# Data Source
The program utilizes CSV files for data, which are expected to have fields for name, gender, and number of births. The CSV files are read from a directory specified by the DIRECTORYPATH constant and are named by year (e.g., yob1880.csv).

using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using CsvHelper;

namespace CasedBasedReasoning
{
    internal class Cases
    {
        public List<Case> CaseList = new List<Case>();
        public Case[] SortedCases;
        private readonly string _inputFile = "\\TRAVEL.CSV";

        public Cases()
        {
            var relPath = Directory.GetCurrentDirectory();
            _inputFile = relPath + _inputFile;
            ReadCases();
        }

        public void CompareCasesToInput(Case inputCase)
        {
            var caseComparer = new CaseComparer(inputCase);
            foreach(var curCase in CaseList)
            {
                caseComparer.SetSimilarity(curCase);
            }
            SortedCases = CaseList.ToArray();
            Array.Sort(SortedCases);
        }

        //These are for reading in the cases from file
        private void ReadCases()
        {
            try
            {
                TextReader textReader = new StreamReader(_inputFile);
                var csvReader = new CsvReader(textReader);
                csvReader.Configuration.HasHeaderRecord = false;
                var rowTypeDefinition = new
                {
                    Empty = string.Empty,
                    FieldName = string.Empty,
                    Value = string.Empty
                };
                var rows = csvReader.GetRecords(rowTypeDefinition).ToArray();

                var rowIndex = 0;
                while (rowIndex <= rows.Length)
                {
                    rowIndex += 3;
                    var curRow = rows[rowIndex];
                    var journeyCode = int.Parse(CleanString(curRow.Value));
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var holidayType = CleanString(curRow.Value);
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var price = int.Parse(CleanString(curRow.Value));
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var numberOfPeople = int.Parse(CleanString(curRow.Value));
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var region = CleanString(curRow.Value);
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var transportation = CleanString(curRow.Value);
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var duration = int.Parse(CleanString(curRow.Value));
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var season = CleanString(curRow.Value);
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var accommodation = CleanString(curRow.Value);
                    rowIndex++;

                    curRow = rows[rowIndex];
                    var hotel = CleanString(curRow.Value);
                    rowIndex += 4;
                    var curCase = new Case(journeyCode, holidayType, price, numberOfPeople, region, transportation, duration, season, accommodation, hotel);
                    CaseList.Add(curCase);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Something went wrong reading in the input file");
                Console.WriteLine(e.ToString());
            }
        }

        private string CleanString(string input)
        {
            var firstStrip = Regex.Replace(input,"[^a-zA-Z0-9]+$", "");
            return Regex.Replace(firstStrip, "^[^a-zA-Z0-9]+", "");
        }
    }
}

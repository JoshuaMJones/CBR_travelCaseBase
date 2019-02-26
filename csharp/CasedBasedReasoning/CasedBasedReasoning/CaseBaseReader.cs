using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using CsvHelper;

namespace CasedBasedReasoning
{
    public class CaseBaseReader
    {
        private readonly string _inputFile = "\\TRAVEL.CSV";
        public CaseBaseReader()
        {
            var relPath = Directory.GetCurrentDirectory();
            _inputFile = relPath + _inputFile;
        }

        public Cases ReadCases()
        {
            var successfulRead = TryReadCases(out var caseList);
            if (!successfulRead)
                return null;
            return new Cases(caseList);
        }

        private bool TryReadCases(out Case[] caseList)
        {
            var tempCaseList = new List<Case>();
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
                while (rowIndex < rows.Length)
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
                    tempCaseList.Add(curCase);
                }
            }
            catch (Exception e)
            {
                caseList = new Case[0];
                return false;
            }

            caseList = tempCaseList.ToArray();
            return true;
        }

        private string CleanString(string input)
        {
            var firstStrip = Regex.Replace(input, "[^a-zA-Z0-9]+$", "");
            return Regex.Replace(firstStrip, "^[^a-zA-Z0-9]+", "");
        }
    }
}

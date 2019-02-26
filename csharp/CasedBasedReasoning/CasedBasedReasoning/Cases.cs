using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Windows.Navigation;
using CsvHelper;

namespace CasedBasedReasoning
{
    public class Cases
    {
        public Case[] CaseList;
        public Case[] SortedCases;

        public Cases(Case[] cases)
        {
            CaseList = cases;
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
    }
}

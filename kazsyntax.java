public String GetSyntaxAnalize() {
       CorrectMorphologicalAnalysis();
       String result = "";
       //result += TextHelper.PrintTitle("Сөз тіркестеріне талдау");
       // сөйлемді алғашқы сөзінен бастап талдай бастаймыз
       for (int i = 0; i < Members.size() - 1; i++) {
           // жаңа сөз тіркесін
           Member first = Members.get(i);
           Member second = Members.get(i + 1);
           if (MorphologyConst.IsQuestionPronoun(first.Word.Root)) {
               Members.remove(i);
               i--;
               continue;
           }
           if (MorphologyConst.IsQuestionPronoun(second.Word.Root)) {
               Members.remove(i+1);
               i--;
               continue;
           }
           // қабысуға тексереміз
           if (IsQabysu(first, second)) {
               //егер сөз тіркес қабыcу болса және бірінші мүше есімше екіншісі зат есім болса, онда тіркесті қиысуға түрлендіреміз
               if ((first.Word.IsEsimshe()) && (second.Word.CurrentPartOfSpeech == MorphologyConst.POS_NOUN)) {
                   first.Word.TransformToJiktikVerb();
                   this.Collocations.add(new Collocation(second, first,
                       SyntaxConst.CT_Qiysu));
               } else
               this.Collocations.add(new Collocation(first, second,
                       SyntaxConst.CT_Qabysu));
              
               //result += printCollocation(SyntaxConst.CT_Qabysu, first, second);
               continue;
           }
           // матасуға тексереміз
           if (IsMatasu(first, second)) {
               Object firstMember = first;
               //егер байланыс матасу болса және бағыныңқы сөзге дейін сөз бар болған болса
               //онда бағыныңқы сөздің қосымшаларын уақытша өшіріп тастап алдыңғы сөзбен қабысуға тексереміз
               if (i > 0) {
                   Member beforeFirst = Members.get(i - 1);
                   Member beforeSecond = Members.get(i);
                   List<Appendix> apps = new ArrayList<Appendix>();

                   while (beforeSecond.Word.haveEnding()) {
                       Appendix app = beforeSecond.Word.Appendixes
                               .get(beforeSecond.Word.Appendixes.size() - 1);
                       beforeSecond.Word.Appendixes.remove(app);
                   }
                  

                   if (IsQabysu(beforeFirst, beforeSecond))
                       firstMember =new Collocation(beforeFirst, beforeSecond,
                               SyntaxConst.CT_Qabysu); else
                   if (IsMengeru(beforeFirst, beforeSecond))
                       firstMember =new Collocation(beforeFirst, beforeSecond,
                                           SyntaxConst.CT_Mengeru);                                   
                  
                   for (Appendix app : apps) {
                       first.Word.Appendixes.add(app);
                   }

               }

               this.Collocations.add(new Collocation(firstMember, second,
                       SyntaxConst.CT_Matasu));
              
               continue;
           }

           // меңгеруге тексереміз
           if (IsMengeru(first, second)) {
               Object firstMember = first;
               //егер байланыс меңгеру болса және бағыныңқы сөзге дейін сөз бар болған болса
               //онда бағыныңқы сөздің қосымшаларын уақытша өшіріп тастап алдыңғы сөзбен қабысуға тексереміз
               if (i > 0) {
                   Member beforeFirst = Members.get(i - 1);
                   Member beforeSecond = Members.get(i);
                   List<Appendix> apps = new ArrayList<Appendix>();

                   while (beforeSecond.Word.haveEnding()) {
                   Appendix app = beforeSecond.Word.Appendixes
                               .get(beforeSecond.Word.Appendixes.size() - 1);
                       beforeSecond.Word.Appendixes.remove(app);
                   }
                   if (IsQabysu(beforeFirst, beforeSecond))
                       firstMember =new Collocation(beforeFirst, beforeSecond,
                               SyntaxConst.CT_Qabysu);
                   for (Appendix app : apps) {
                       first.Word.Appendixes.add(app);
                   }
               }
               this.Collocations.add(new Collocation(firstMember, second,
                       SyntaxConst.CT_Mengeru));
               continue;
           }

           if (IsQiysu(first, second)) {
               Object firstMember = first;
              
               //егер байланыс қиысу болса және алдыңғы тіркес меңгеру болса
               if (this.Collocations.size()>0)
               {
                   Collocation before = this.Collocations.get(this.Collocations.size()-1);
                   if (before.Collocation.equals(SyntaxConst.CT_Mengeru) &&
                       ((Word)before.Second).Root.equals(first.Word.Root))
                   {
                       firstMember = this.Collocations.get(this.Collocations.size()-1);
                       this.Collocations.remove(this.Collocations.size()-1);
                       this.Collocations.add(new Collocation(firstMember, second,
                       SyntaxConst.CT_Qiysu));
                       continue;
                   }
               }
              
               //егер байланыс қиысу болса және бағыныңқы сөзге дейін сөз бар болған болса
               //онда бағыныңқы сөздің қосымшаларын уақытша өшіріп тастап алдыңғы сөзбен қабысуға тексереміз
               if (i > 0) {
                   Member beforeFirst = Members.get(i - 1);
                   Member beforeSecond = Members.get(i);
                   List<Appendix> apps = new ArrayList<Appendix>();

                   while (beforeSecond.Word.haveEnding()) {
                   Appendix app = beforeSecond.Word.Appendixes
                               .get(beforeSecond.Word.Appendixes.size() - 1);
                       beforeSecond.Word.Appendixes.remove(app);
                   }
                  

                   if (IsQabysu(beforeFirst, beforeSecond))
                       firstMember =new Collocation(beforeFirst, beforeSecond,
                               SyntaxConst.CT_Qabysu);
                  
                   for (Appendix app : apps) {
                       first.Word.Appendixes.add(app);
                   }

               }
               this.Collocations.add(new Collocation(firstMember, second,
                       SyntaxConst.CT_Qiysu));
               continue;
           }
          
           if (IsJanasu(first, second))
           {
               this.Collocations.add(new Collocation(first, second, SyntaxConst.CT_Janasu));
               continue;
           }

       }

        for (Collocation collocation : this.Collocations) {
        result += collocation.toString() + " <br/>";
        }
       return result;
    }

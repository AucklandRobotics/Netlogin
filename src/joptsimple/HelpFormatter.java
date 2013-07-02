package joptsimple;

import java.util.*;
import joptsimple.internal.Classes;
import joptsimple.internal.ColumnarData;
import joptsimple.internal.Strings;

// Referenced classes of package joptsimple:
//            AbstractOptionSpec, OptionSpecVisitor, ArgumentAcceptingOptionSpec, AlternativeLongOptionSpec, 
//            ParserRules, NoArgumentOptionSpec, RequiredArgumentOptionSpec, OptionalArgumentOptionSpec

class HelpFormatter
    implements OptionSpecVisitor
{

    HelpFormatter()
    {
    }

    String format(Map map)
    {
        if(map.isEmpty())
            return "No options specified";
        grid.clear();
        Comparator comparator = new Comparator() {

            public int compare(AbstractOptionSpec abstractoptionspec1, AbstractOptionSpec abstractoptionspec2)
            {
                return ((String)abstractoptionspec1.options().iterator().next()).compareTo((String)abstractoptionspec2.options().iterator().next());
            }

            public int compare(Object obj, Object obj1)
            {
                return compare((AbstractOptionSpec)obj, (AbstractOptionSpec)obj1);
            }

            final HelpFormatter this$0;
            {
                this$0 = HelpFormatter.this;
                super();
            }
        }
;
        TreeSet treeset = new TreeSet(comparator);
        treeset.addAll(map.values());
        AbstractOptionSpec abstractoptionspec;
        for(Iterator iterator = treeset.iterator(); iterator.hasNext(); abstractoptionspec.accept(this))
            abstractoptionspec = (AbstractOptionSpec)iterator.next();

        return grid.format();
    }

    void addHelpLineFor(AbstractOptionSpec abstractoptionspec, String s)
    {
        grid.addRow(new Object[] {
            (new StringBuilder()).append(createOptionDisplay(abstractoptionspec)).append(s).toString(), abstractoptionspec.description()
        });
    }

    public void visit(NoArgumentOptionSpec noargumentoptionspec)
    {
        addHelpLineFor(noargumentoptionspec, "");
    }

    public void visit(RequiredArgumentOptionSpec requiredargumentoptionspec)
    {
        visit(((ArgumentAcceptingOptionSpec) (requiredargumentoptionspec)), '<', '>');
    }

    public void visit(OptionalArgumentOptionSpec optionalargumentoptionspec)
    {
        visit(((ArgumentAcceptingOptionSpec) (optionalargumentoptionspec)), '[', ']');
    }

    private void visit(ArgumentAcceptingOptionSpec argumentacceptingoptionspec, char c, char c1)
    {
        String s = argumentacceptingoptionspec.argumentDescription();
        String s1 = nameOfArgumentType(argumentacceptingoptionspec);
        StringBuilder stringbuilder = new StringBuilder();
        if(s1.length() > 0)
        {
            stringbuilder.append(s1);
            if(s.length() > 0)
                stringbuilder.append(": ").append(s);
        } else
        if(s.length() > 0)
            stringbuilder.append(s);
        String s2 = stringbuilder.length() != 0 ? (new StringBuilder()).append(' ').append(Strings.surround(stringbuilder.toString(), c, c1)).toString() : "";
        addHelpLineFor(argumentacceptingoptionspec, s2);
    }

    public void visit(AlternativeLongOptionSpec alternativelongoptionspec)
    {
        addHelpLineFor(alternativelongoptionspec, (new StringBuilder()).append(' ').append(Strings.surround(alternativelongoptionspec.argumentDescription(), '<', '>')).toString());
    }

    private String createOptionDisplay(AbstractOptionSpec abstractoptionspec)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = abstractoptionspec.options().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            stringbuilder.append(s.length() <= 1 ? ParserRules.HYPHEN : "--");
            stringbuilder.append(s);
            if(iterator.hasNext())
                stringbuilder.append(", ");
        } while(true);
        return stringbuilder.toString();
    }

    private static String nameOfArgumentType(ArgumentAcceptingOptionSpec argumentacceptingoptionspec)
    {
        Class class1 = argumentacceptingoptionspec.argumentType();
        return java/lang/String.equals(class1) ? "" : Classes.shortNameOf(class1);
    }

    private final ColumnarData grid = new ColumnarData(new String[] {
        "Option", "Description"
    });
}

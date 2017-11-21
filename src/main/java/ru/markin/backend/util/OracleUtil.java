package ru.markin.backend.util;

import java.util.List;

import javax.annotation.Nonnull;

import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

public class OracleUtil
{
    private static final int IN_MAX = 1000;

    public static Disjunction in(@Nonnull String key, @Nonnull final List<?> list)
    {
        final Disjunction or = Restrictions.or();
        final int size = list.size();
        final int to = size > IN_MAX ? IN_MAX : size;
        for (int from = 0; from < to; from += IN_MAX)
        {
            or.add(Restrictions.in(key, list.subList(from, to)));
        }
        return or;
    }
}

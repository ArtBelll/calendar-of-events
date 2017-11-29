package ru.korbit.cecommon.packet;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Converter<D, E> {

    private final Function<D, E> fromDto;
    private final Function<E, D> fromEntity;

    protected Converter(Function<D, E> fromDto, Function<E, D> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final E convertFromDto(final D userDto) {
        return fromDto.apply(userDto);
    }

    public final D convertFromEntity(final E user) {
        return fromEntity.apply(user);
    }

    public final List<E> createFromDtos(final Collection<D> dtoUsers) {
        return dtoUsers.stream().map(this::convertFromDto).collect(Collectors.toList());
    }

    public final List<D> createFromEntities(final Collection<E> users) {
        return users.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }
}

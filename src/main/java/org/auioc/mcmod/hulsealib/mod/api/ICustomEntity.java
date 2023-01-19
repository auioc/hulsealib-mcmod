package org.auioc.mcmod.hulsealib.mod.api;

public interface ICustomEntity extends ICustomModelObject {

    @Override
    default Type getDataAccessorType() { return Type.ENTITY; }

}

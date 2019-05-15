package ru.wkn.jni;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.wkn.jni.functions.IAssemblerFunctions;

/**
 * Class {@code IAssemblerFunctions} represent invocation for math assembler functions.
 *
 * @author Alexey Konev
 */
@AllArgsConstructor
@Getter
@Setter
public class AssemblerMath {

    /**
     * Field of {@code IAssemblerFunctions} object represent invocation for math assembler functions.
     */
    private IAssemblerFunctions assemblerFunctions;
}

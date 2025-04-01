// src/directives/decimalFormat.ts
import { Directive, nextTick } from 'vue';

interface DecimalFormatOptions {
    max?: number;
}

const decimalFormat: Directive = {
    mounted(el: HTMLElement, binding) {
        const input = el.querySelector('input') as HTMLInputElement;
        let rawValue: string = '';

        const maxValue: number = (binding.value as DecimalFormatOptions)?.max ?? 1000000000;

        const formatNumber = (value: string): string => {
            // Chỉ giữ số và dấu chấm
            let numericValue = value.replace(/[^0-9.]/g, '');

            // Đảm bảo chỉ có một dấu chấm
            const parts = numericValue.split('.');
            if (parts.length > 2) {
                numericValue = `${parts[0]}.${parts.slice(1).join('')}`;
            }

            // Xử lý phần nguyên
            let integerPart = parts[0] || '';
            integerPart = integerPart.replace(/^0+/, '');

            if (!integerPart && !parts[1]) {
                rawValue = '';
                return '';
            }

            if (!integerPart && parts[1]) {
                integerPart = '0';
            }

            const numValue = Number(numericValue || integerPart);
            if (numValue > maxValue) {
                numericValue = maxValue.toString();
            } else {
                numericValue = parts[1] !== undefined ? `${integerPart}.${parts[1]}` : integerPart;
            }

            rawValue = numericValue;
            const formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            return parts[1] !== undefined ? `${formattedInteger}.${parts[1]}` : formattedInteger;
        };

        input.addEventListener('input', (e: Event) => {
            const target = e.target as HTMLInputElement;
            const formatted = formatNumber(target.value);
            target.value = formatted;
            if (binding.instance && binding.arg) {
                binding.instance[binding.arg] = formatted;
            }
        });

        input.addEventListener('blur', (e: Event) => {
            const target = e.target as HTMLInputElement;
            if (!rawValue) {
                target.value = '';
            } else {
                target.value = formatNumber(rawValue);
            }
            if (binding.instance && binding.arg) {
                binding.instance[binding.arg] = target.value;
            }
        });

        nextTick(() => {
            if (input.value) {
                input.value = formatNumber(input.value);
                rawValue = input.value.replace(/[^0-9.]/g, '');
            }
        });
    },
};

export default decimalFormat;
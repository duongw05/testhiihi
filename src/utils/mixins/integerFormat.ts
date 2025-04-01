// src/directives/integerFormat.ts
import { Directive, nextTick } from 'vue';

interface IntegerFormatOptions {
    max?: number;
}

const integerFormat: Directive = {
    mounted(el: HTMLElement, binding) {
        const input = el.querySelector('input') as HTMLInputElement;
        let rawValue: string = '';

        const maxValue: number = (binding.value as IntegerFormatOptions)?.max ?? 1000000000;

        const formatNumber = (value: string): string => {
            // Chỉ giữ số, loại bỏ mọi ký tự khác
            let numericValue = value.replace(/[^0-9]/g, '');

            // Xóa số 0 ở đầu
            numericValue = numericValue.replace(/^0+/, '');

            if (!numericValue) {
                rawValue = '';
                return '';
            }

            const numValue = Number(numericValue);
            if (numValue > maxValue) {
                numericValue = maxValue.toString();
            }

            rawValue = numericValue;
            return numericValue.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
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
                rawValue = input.value.replace(/[^0-9]/g, '');
            }
        });
    },
};

export default integerFormat;
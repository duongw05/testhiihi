// eventBus.ts
import mitt from 'mitt';

type Events = {
    'custom-event': string; // Định nghĩa các sự kiện và kiểu dữ liệu của chúng
};

const eventBus = mitt<Events>();

export default eventBus;

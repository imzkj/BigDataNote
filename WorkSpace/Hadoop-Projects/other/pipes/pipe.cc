#include <algorithm>
#include <limits>
#include <stdint.h>
#include <string>
#include <string.h>
#include "hadoop/Pipes.hh"
#include "hadoop/TemplateFactory.hh"
#include "hadoop/StringUtils.hh"

using namespace std;
class WordCountMapper : public HadoopPipes::Mapper {
public:
	WordCountMapper(HadoopPipes::TaskContext& context) {
	}
	void map(HadoopPipes::MapContext& context) {
		string line = context.getInputValue();
		char buf[1024];
		strcpy(buf, line.c_str());
		char *p = strtok(buf, " ");
		while(p!=NULL)
		{
			context.emit(p, "1");
			p = strtok(NULL, " ");
		}
	}
};

class WordCountReducer : public HadoopPipes::Reducer {
public:
	WordCountReducer(HadoopPipes::TaskContext& context) {
	}
	void reduce(HadoopPipes::ReduceContext& context) {
		int n = 0;
		while (context.nextValue()) {
			n += HadoopUtils::toInt(context.getInputValue());
		}
		context.emit(context.getInputKey(), HadoopUtils::toString(n));
	}
};

int main(int argc, char *argv[]) {
	return HadoopPipes::runTask(HadoopPipes::TemplateFactory<WordCountMapper,WordCountReducer>());
}
